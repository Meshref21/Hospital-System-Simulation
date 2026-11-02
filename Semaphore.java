public class Semaphore {
    private int value;

    public Semaphore(int initial) {
        value = initial;
    }

    public synchronized void acquire() {
        while (value == 0) {
            try { wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value--;
    }

    public synchronized void release() {
        value++;
        notify();
    }

    public synchronized boolean tryAcquire(long timeout) {
        long deadline = System.currentTimeMillis() + timeout;
        while (value == 0) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) {
                return false;
            }
            try { wait(remaining);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value--;
        return true;
    }
}