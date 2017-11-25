package monitorsynch;

/**
 * Created by Алексей on 25.11.2017.
 */
public class Qwe {
    User user = new User(0, 0);
    Integer a = 0;

    public void dosmth() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    user.addAmount(10);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.addAmount(100);
                }
            }
        };
        t1.start();
        Thread.sleep(100);
        synchronized (a) {
            user.addAmount(1000);
        }
        System.out.println(user.getAmount());       //1010 - Тысяча уже добавилась, до сотни. Почему?
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(user.getAmount());
    }




    public static void main(String[] args) throws InterruptedException {
        Qwe qwe = new Qwe();
        qwe.dosmth();

}
}
