package nl.abramkin.pirates;

import junit.framework.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/java/beans.xml")
public class AppTest extends TestCase {

    @Autowired
    private JackSparrowHelperImpl fairlyHelper;

    public AppTest() {
        super("Test");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Test App
     */
    @org.junit.Test
    public void testApp() {
        final String path = "sources.csv";

        Purchases purchases = fairlyHelper.helpJackSparrow(path, 555);
        assertTrue(purchases.calculateAveragePrice().intValue() == 51);

        purchases = fairlyHelper.helpJackSparrow(path, 500);
        assertTrue(purchases.calculateAveragePrice() == 50.7);

        purchases = fairlyHelper.helpJackSparrow(path, 10);
        assertTrue(purchases.calculateAveragePrice().intValue() == 50);

        purchases = fairlyHelper.helpJackSparrow(path, 0);
        assertTrue(purchases.getPurchases().isEmpty());

        purchases = fairlyHelper.helpJackSparrow(path, 1000);
        assertNull(purchases);

        purchases = fairlyHelper.helpJackSparrow(path, 200);
        assertTrue(purchases.calculateAveragePrice() == 50.5);
    }
}
