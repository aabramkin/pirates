package nl.abramkin.pirates;

import junit.framework.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Test App
     */
    public void testApp()
    {
        final String path = "sources.csv";
        JackSparrowHelper helper = JackSparrowHelperImpl.getInstance();

        Purchases purchases = helper.helpJackSparrow(path, 555);
        assertTrue(purchases.calculateAveragePrice().intValue() == 51);

        purchases = helper.helpJackSparrow(path, 500);
        assertTrue(purchases.calculateAveragePrice() == 50.7);

        purchases = helper.helpJackSparrow(path, 10);
        assertTrue(purchases.calculateAveragePrice().intValue() == 50);

        purchases = helper.helpJackSparrow(path, 0);
        assertTrue(purchases.getPurchases().isEmpty());

        purchases = helper.helpJackSparrow(path, 1000);
        assertNull(purchases);
    }
}
