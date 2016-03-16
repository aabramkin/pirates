package nl.abramkin.pirates.calc;

import com.sun.istack.internal.NotNull;
import nl.abramkin.pirates.storage.PriceSource;

import java.util.List;

/**
 * Created by aabramkin on 09/03/16.
 */
public class CalculatorFactory {
    public final static String TYPE_DUMMY_CALCULATOR = "D_CALC";
    public final static String TYPE_FAIRLY_CALCULATOR = "F_CALC";

    public static ICalculator get(int quantity, List<PriceSource> prices, @NotNull String type) {
        if (TYPE_DUMMY_CALCULATOR.equals(type)) {
            return new FairlyCalculator(quantity, prices);
        }
        if (TYPE_FAIRLY_CALCULATOR.equals(type)) {
            return new FairlyCalculator(quantity, prices);
        }
        return null;
    }
}
