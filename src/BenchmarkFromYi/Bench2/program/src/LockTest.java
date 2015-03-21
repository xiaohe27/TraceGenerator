import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

  public static int sum = 0;

    static class MyObj {
        Lock lock;

        public MyObj(Lock lock) {
            this.lock = lock;
        }
        public Lock acquire() {
            this.lock.lock();
            return this.lock;
        }
        public Lock release() {
            this.lock.unlock();
            return this.lock;
        }

        public void f(int num) {
            for (int i = 0; i < num; i++) {
                this.acquire();
            }

            for (int i = 0; i < num; i++) {
                this.release();
            }

        }
    }

  public static void main(String[] args) {
    MyObj myObj1 = new MyObj(new ReentrantLock());

      myObj1.f(10);
    
  }
}



  
