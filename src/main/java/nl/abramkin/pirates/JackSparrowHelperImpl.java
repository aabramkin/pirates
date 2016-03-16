package nl.abramkin.pirates;

import nl.abramkin.pirates.calc.*;
import nl.abramkin.pirates.storage.*;

/**
 * Created by aabramkin on 05/03/16.
 */
public class JackSparrowHelperImpl implements JackSparrowHelper {
    private static JackSparrowHelper instance = null;

    private JackSparrowHelperImpl() {
    }

    public static synchronized JackSparrowHelper getInstance() {
        if (instance == null) {
            instance = new JackSparrowHelperImpl();
        }
        return instance;
    }

    public Purchases helpJackSparrow(String pathToPrices, int numberOfGallons) {
        IPriceStorage storage = new PriceStorage(pathToPrices);
        ICalculator calculator = CalculatorFactory.get(numberOfGallons, storage.getPriceSources(), CalculatorFactory.TYPE_FAIRLY_CALCULATOR);
        return calculator != null ? calculator.calculate() : null;
    }
}
