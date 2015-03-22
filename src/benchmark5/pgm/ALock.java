package pgm;

// a crafted lock class
public class ALock {

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
