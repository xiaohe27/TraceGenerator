package code4;

import common.BadTest;

/**
 * Created by xiaohe on 3/16/15.
 */
public class Bad extends BadTest {
    private void bad() {
        MyObj myObj = new MyObj();
        myObj.B();
        myObj.A();
        myObj.done();
    }

    @Override
    public void run1() {
        this.bad();
    }

    @Override
    public void run2() {
        this.bad();
    }

    @Override
    public void run3() {
        this.bad();
    }
}
