import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * Created by aabramkin on 15/03/16.
 */
public class ThreadTest {
    private final Lock lock = new ReentrantLock();


    public static void main(String[] args) {
        final MicroBlogNode first = new MicroBlogNode("First");
        final MicroBlogNode second = new MicroBlogNode("Second");
        final String update1 = "Update 1";
        final String update2 = "Update 2";

        new Thread(new Runnable() {
            public void run() {
                first.propagateUpdate(update1, second);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                second.propagateUpdate(update2, first);
            }
        }).start();
    }

    public static class MicroBlogNode {
        private final Lock lock = new ReentrantLock();

        private final String ident;

        public MicroBlogNode(String ident) {
            this.ident = ident;
        }

        public void propagateUpdate(String update, MicroBlogNode backup) {
            boolean acquired = false;
            boolean done = false;
            while (!done) {
                int wait = (int) (Math.random() * 10);
                try {
                    acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);
                    if (acquired) {
                        System.out.println(ident + ": received: " + update);
                        done = backup.confirmUpdate(update, this);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (acquired) {
                        lock.unlock();
                    }
                }
                if (!done) {
                    try {
                        Thread.sleep(wait);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public boolean confirmUpdate(String update, MicroBlogNode backup) {
            boolean acquired = false;
            lock.lock();
            try {
                int wait = (int) (Math.random() * 10);
                acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);
                if (acquired) {
                    System.out.println(ident + ": confirmed: " + update + " from " + backup.ident);
                    return true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (acquired) {
                    lock.unlock();
                }
            }
            return false;
        }
    }
}
