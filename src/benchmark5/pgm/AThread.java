package pgm;

// a crafted thread class
public class AThread {

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
           //System.err.println("Thead " + name + " FAILED:Lock " + l.name + " is accquired more times");
       } else if ( l.counter < 0) {
           //System.err.println("Thread " + name + " FAILED:Lock " + l.name + " is released more times");
       } else {
           //System.err.println("Thread " + name + " SUCCEEDED:Lock " + l.name + " is accuqired and released equivalent times");
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
            //System.err.println("Sum is :" + result);
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

