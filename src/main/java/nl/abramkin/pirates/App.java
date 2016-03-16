package nl.abramkin.pirates;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class App {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new RuntimeException(String.format("Wrong number of arguments %1$s", args.length));
        }
        String path = args[0];
        int numOfGallons = Integer.parseInt(args[1]);

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"beans.xml"});

        JackSparrowHelper helper = context.getBean("fairlyHelper", JackSparrowHelper.class);
        helper.helpJackSparrow(path, numOfGallons);
    }
}
