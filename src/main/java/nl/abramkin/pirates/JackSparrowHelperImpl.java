package nl.abramkin.pirates;

import nl.abramkin.pirates.calc.ICalculator;
import nl.abramkin.pirates.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by aabramkin on 05/03/16.
 */
@Component
public class JackSparrowHelperImpl implements JackSparrowHelper {
    final private ICalculator calculator;

    @Autowired
    public JackSparrowHelperImpl(ICalculator calculator) {
        this.calculator = calculator;
    }

    public Purchases helpJackSparrow(String pathToPrices, int numberOfGallons) {
        IPriceStorage storage = new PriceStorage(pathToPrices);
        return calculator != null ? calculator.calculate(numberOfGallons, storage.getPriceSources()) : null;
    }
}
