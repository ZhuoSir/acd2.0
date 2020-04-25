package bean;

import com.yuntongxun.acd.common.LineElement;

public class Customer extends LineElement {

    String index;

    public Customer(String index) {
        this.index = index;
    }

    public Customer(String index, String groupId) {
        super.groupId = groupId;
        this.index = index;
    }

    @Override
    public String index() {
        return index;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "index='" + index + '\'' +
                '}';
    }
}
