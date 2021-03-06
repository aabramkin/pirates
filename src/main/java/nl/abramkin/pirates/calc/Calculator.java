package nl.abramkin.pirates.calc;

import nl.abramkin.pirates.*;
import nl.abramkin.pirates.storage.PriceSource;
import org.slf4j.*;

import java.util.*;

/**
 * Created by aabramkin on 05/03/16.
 */
public class Calculator extends AbstractCalculator implements Comparator<PriceSource> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    protected Purchases calculate() {
        if (getPrices().isEmpty()) {
            return null;
        }
        LOGGER.info("Calculate asks");
        List<PriceSource> sources = new ArrayList<PriceSource>(getPrices());
        Collections.sort(sources, this);
        Set<Purchase> purchases = new HashSet<Purchase>();
        int currentQuantity = getQuantity();
        while (currentQuantity > 0) {
            if (sources.isEmpty()) {
                break;
            }
            PriceSource source = sources.remove(0);
            int quantity = source.getMaxQuantityForAsk(currentQuantity);
            if (quantity > 0) {
                Purchase purchase = new Purchase();
                purchase.setNumberOfGallons(quantity);
                purchase.setSourceName(source.getName());
                purchase.setPriceOfGallon(source.getAvgPrice());
                purchases.add(purchase);
                currentQuantity -= quantity;
                LOGGER.info(purchase.toString());
            }
        }
        if (currentQuantity > 0) {
            return null;
        }
        Purchases result = new Purchases(getQuantity());
        result.setPurchases(purchases);
        return result;
    }

    public int compare(PriceSource o1, PriceSource o2) {
        if (o1.getAvgPrice() != o2.getAvgPrice()) {
            return o1.getAvgPrice() < o2.getAvgPrice() ? -1 : 1;
        }
        if (o1.getMinSize() != o2.getMinSize()) {
            return o1.getMinSize() < o2.getMinSize() ? -1 : 1;
        }
        if (o1.getSize() != o2.getSize()) {
            return o1.getSize() < o2.getSize() ? -1 : 1;
        }
        if (o1.getStepSize() != o2.getStepSize()) {
            return o1.getStepSize() < o2.getStepSize() ? -1 : 1;
        }
        return o1.getName().compareTo(o2.getName());
    }
}
