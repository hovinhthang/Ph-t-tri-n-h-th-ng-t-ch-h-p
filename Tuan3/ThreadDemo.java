
class ThreadDemo {
    public static void main(String args[]) {
    Thread t = Thread.currentThread();
    t.setName("My Thread");
    System.out.println("Current thread: " + t);
    try {
    for (int i=0; i <5; i++) {
    System.out.println(i);
    Thread.sleep(1000);
    }
    } catch (InterruptedException e) {}
    }
    }
    class A extends Thread {
        public void run() {
        for (int i=0; i<10; i++) {
        System.out.print(i + " ");
        try {
        Thread.sleep(1000);
        } catch (InterruptedException e) {}
        }
        }
        }
        class B extends Thread {
        public void run() {
        for (int i=0; i<10; i++) {
            System.out.print((char) (i+65) + " ");
            try {
            Thread.sleep(2500);
            }
            catch (InterruptedException e) {}
            }
            }
            }
            class C {
            public static void main(String args[]) {
            new A().start();
            new B().start();
            }
            }        