package nl.abramkin.pirates;

/**
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if(args.length != 2) {
            throw new RuntimeException(String.format("Wrong number of arguments %1$s", args.length));
        }
        String path = args[0];
        int numOfGallons = Integer.parseInt(args[1]);
        JackSparrowHelper helper = JackSparrowHelperImpl.getInstance();
        helper.helpJackSparrow(path, numOfGallons);
    }
}
