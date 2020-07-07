package liulizhong;

public class Thread08_Atomic {
    public static void main(String[] args) throws Exception {
        ShareData11 sd11 = new ShareData11();
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                sd11.print5();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= 3; i++) {
                sd11.print10();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= 3; i++) {
                sd11.print15();
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }

}

class ShareData11 {

    public synchronized void print5() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
    }

    public synchronized void print10() {
        for (int i = 6; i <= 10; i++) {
            System.out.println("i = " + i);
        }
    }

    public synchronized void print15() {
        for (int i = 11; i <= 15; i++) {
            System.out.println("i = " + i);
        }
    }
}