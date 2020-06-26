package bean;

import com.yuntongxun.acd.common.LineElement;

import java.io.Serializable;

public class Customer extends LineElement implements Serializable {

    private static final long serialVersionUID = 1L;

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
