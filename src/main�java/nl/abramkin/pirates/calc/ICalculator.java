package nl.abramkin.pirates.calc;

import nl.abramkin.pirates.Purchases;
import nl.abramkin.pirates.storage.PriceSource;

import java.util.List;

/**
 * Created by aabramkin on 05/03/16.
 */
public interface ICalculator {
    Purchases calculate(int quantity, List<PriceSource> prices);
}
