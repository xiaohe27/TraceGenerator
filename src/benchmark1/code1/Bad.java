package code1;

import common.BadTest;
import java.util.*;

/**
 * Created by xiaohe on 3/16/15.
 */
public class Bad extends BadTest {

    private void bad() {
	Map<String, String> testMap = new HashMap<String,String>();
        testMap.put("key"+ testID,"val"+ testID); //update map

        Set<String> keys = testMap.keySet(); //create collection

        Iterator iterator = keys.iterator(); //create iterator

        testMap.put("anotherkey"+ testID,"anotherval"+ testID); //update map

        try {
	iterator.next(); //use iterator
	} catch(Exception e) {}
	
    }

    public void run1() { // next next
	bad();
    }

    public void run2() { //hasnextfalse next
	bad();
    }

    public void run3() {  //hasNextTrue from a different obj
	bad();
    }
}
