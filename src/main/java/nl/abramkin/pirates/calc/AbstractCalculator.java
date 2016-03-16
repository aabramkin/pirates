package nl.abramkin.pirates.calc;

import nl.abramkin.pirates.Purchases;
import nl.abramkin.pirates.storage.PriceSource;

import java.util.List;

/**
 * Created by aabramkin on 16/03/16.
 */
public abstract class AbstractCalculator implements ICalculator {
    private int quantity;
    private List<PriceSource> prices;

    public Purchases calculate(int quantity, List<PriceSource> prices) {
        this.quantity = quantity;
        this.prices = prices;
        return calculate();
    }

    protected abstract Purchases calculate();

    public int getQuantity() {
        return quantity;
    }

    public List<PriceSource> getPrices() {
        return prices;
    }

}
