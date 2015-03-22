package pgm;

public class SafeLock {

    public static void main(String[] args) {

	System.out.print("event, ALock, AThread\r\n");
    
        int depth = 100;
        int outer = 100;
        int inner = 1000;
        int threadNum = 50;

	if(args.length == 4) {
	    depth = Integer.parseInt(args[0]);
	    outer = Integer.parseInt(args[1]);
	    inner = Integer.parseInt(args[2]);
	    threadNum = Integer.parseInt(args[3]);
	}

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
