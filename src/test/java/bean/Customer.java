package bean;

import com.yuntongxun.acd.common.LineElement;

public class Customer extends LineElement {

    String index;

    public Customer(String index) {
        this.index = index;
    }

    @Override
    public String index() {
        return index;
    }
}
