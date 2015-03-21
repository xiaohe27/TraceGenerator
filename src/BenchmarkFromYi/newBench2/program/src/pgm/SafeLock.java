package pgm;
// a crafted lock class
class ALock {

    public int counter;
    
    public String name;

    public ALock(String lockname) {
       counter = 0;
       name = lockname;
    }

    public void inc() {
       counter++;
    }
  
    public void dec() {
       counter--;
    }
}

// a crafted thread class
class AThread {

    public int sum_aim;
    
    public String name;

    public AThread(String threadname, int aim) {
        sum_aim = aim;
        name = threadname;
    }

    private void begin() {

    }

    private void end() {

    }

    private void acquire(ALock l){
        l.inc();
    }

    private void release(ALock l){
        l.dec();
    }

    private void checkLock(ALock l) {
       if(l.counter > 0) {
           System.out.println("Thead " + name + " FAILED:Lock " + l.name + " is accquired more times");
       } else if ( l.counter < 0) {
           System.out.println("Thread " + name + " FAILED:Lock " + l.name + " is released more times");
       } else {
           System.out.println("Thread " + name + " SUCCEEDED:Lock " + l.name + " is accuqired and released equivalent times");
       }
    }

    private int doCalc(){
        int sum = 0;
        if ( sum_aim > 0) {
            for( int i = 1; i <= sum_aim; i++) {
                sum += i;
            }
        }
        return sum;
    }

    public void doSomethingHelper(ALock l, int depth) {
        if ( depth == 0) {
            int result = doCalc();
            System.out.println("Sum is :" + result);
        } else {
            this.acquire(l);
            doSomethingHelper(l, depth - 1);
            this.release(l);
        }
    }

    public void doSomething(ALock l, int depth, boolean good) {
        begin();
        doSomethingHelper(l, depth);
        if (!good){
            this.release(l);
        }
        checkLock(l);
        end();
    }

}


public class SafeLock {

    public static void main(String[] args) {
    
        int depth = 100;
        int outer = 100;
        int inner = 1000;
        int threadNum = 50;

        for(int i = 0; i <= outer; i++) {
            String l1name = "Lock_O#" + Integer.toString(i);
            ALock l1 = new ALock(l1name);
            AThread[] arr = new AThread[threadNum];
            for(int j = 0; j < threadNum; j++) {
                String t1name = "Thread_O#" + Integer.toString(i) + "#" + Integer.toString(j);
                arr[j] = new AThread(t1name, j);
                arr[j].doSomething(l1, depth, true);
            }
            for(int k = 0; k < inner; k++) {
                String l2name = "Lock_I#" + Integer.toString(i) + "#" + Integer.toString(k);
                ALock l2 = new ALock(l2name);
                String t2name = "Thread_I#" + Integer.toString(i) + "#" + Integer.toString(k);
                AThread t = new AThread(t2name, k);
                t.doSomething(l2, depth, true);
                int index = k % threadNum;
                arr[index].doSomething(l2, depth, true);
            }
            if(i == outer) {
                int index = i % threadNum;
                arr[index].doSomething(l1, depth, false);
            }
        }
            
    
   }
}
