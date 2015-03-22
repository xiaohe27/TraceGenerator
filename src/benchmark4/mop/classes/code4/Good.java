package code4;

import common.GoodTest;

/**
 * Created by xiaohe on 3/16/15.
 */
public class Good extends GoodTest {


    @Override
    public void run1() { 
          MyObj obj = new MyObj();
        for (int i = 0; i < this.testID; i++) {
            obj.A();
            obj.B();
            obj.C();
        }
        obj.done();
    }

    @Override
    public void run2() {
        MyObj obj = new MyObj();
        for (int i = 0; i < this.testID; i++) {
            obj.A();
            obj.C();
            obj.C();
            obj.B();
            obj.A();
            obj.B();
        }
        obj.done();
    }

    @Override
    public void run3() {
        MyObj obj = new MyObj();
        MyObj obj2 = new MyObj();
        for (int i = 0; i < this.testID; i++) {
            obj2.A();
            obj.C();
            obj2.C();
            obj.B();
            obj.A();
            obj2.B();
        }
        obj.done();
        obj2.done();
    }
}
