package bean;

import com.yuntongxun.acd.common.LineServant;

public class Agent extends LineServant {

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
                "sort=" + sort +
                ", distributeTimes=" + distributeTimes +
                '}';
    }
}
