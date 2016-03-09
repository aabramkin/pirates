package nl.abramkin.pirates.calc;

import nl.abramkin.pirates.*;
import nl.abramkin.pirates.storage.PriceSource;
import org.slf4j.*;

import java.util.*;

/**
 * Created by aabramkin on 09/03/16.
 */
public class FairlyCalculator implements ICalculator, Comparator<PriceSource> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FairlyCalculator.class);

    private final int quantity;
    private final List<PriceSource> prices;
    private Double minPrice;
    private Purchases minPurchases;

    public FairlyCalculator(int quantity, List<PriceSource> prices) {
        this.quantity = quantity;
        this.prices = new ArrayList<PriceSource>(prices);
    }

    public Purchases calculate() {
        ArrayList<PriceSource> prices = new ArrayList<PriceSource>(this.prices);
        Collections.sort(prices, this);
        permute(prices, 0);
        if (minPurchases == null)
            return null;
        StringBuilder builder = new StringBuilder();
        for (Purchase p : minPurchases.getPurchases()) {
            builder.append(p).append(" ");
        }
        if (!minPurchases.getPurchases().isEmpty()) {
            LOGGER.info(String.format("Solve task: %1$s", builder.toString()));
        }
        return minPurchases;
    }

    void permute(List<PriceSource> arr, int k) {
        for (int i = k; i < arr.size(); i++) {
            Collections.swap(arr, i, k);
            permute(arr, k + 1);
            Collections.swap(arr, k, i);
        }
        if (k == arr.size() - 1) {
            Purchases purchases = getPurchasesFromSources(arr);
            if (purchases != null) {
                Double currentPrice = purchases.calculateAveragePrice();
                if (minPrice == null || minPrice >= currentPrice) {
                    minPrice = currentPrice;
                    minPurchases = purchases;
                }
            }
        }
    }

    private Purchases getPurchasesFromSources(List<PriceSource> sources) {
        Set<Purchase> purchases = new HashSet<Purchase>();
        int currentQuantity = quantity;
        for (PriceSource source : sources) {
            if (currentQuantity <= 0) {
                break;
            }
            int quantity = source.getMaxQuantityForAsk(currentQuantity);
            if (quantity > 0) {
                Purchase purchase = new Purchase();
                purchase.setNumberOfGallons(quantity);
                purchase.setSourceName(source.getName());
                purchase.setPriceOfGallon(source.getAvgPrice());
                purchases.add(purchase);
                currentQuantity -= quantity;
            }
        }
        if (currentQuantity != 0) {
            return null;
        }
        Purchases result = new Purchases(quantity);
        result.setPurchases(purchases);
        return result;
    }

    public int compare(PriceSource o1, PriceSource o2) {
        if (o1.getAvgPrice() != o2.getAvgPrice()) {
            return o1.getAvgPrice() < o2.getAvgPrice() ? -1 : 1;
        }
        return o1.getName().compareTo(o2.getName());
    }

}
