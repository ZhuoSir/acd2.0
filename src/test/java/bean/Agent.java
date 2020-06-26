package bean;

import com.yuntongxun.acd.common.LineServant;

import java.io.Serializable;

public class Agent extends LineServant implements Serializable {

    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Agent(String servantId) {
        super(servantId);
    }

    public Agent(String servantId, int sort) {
        super(servantId);
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "sevantID=" + getServantId() +
                ", sort=" + sort +
                ", distributeTimes=" + distributeTimes +
                '}';
    }
}
