package bean;

import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.distribute.comparator.LineServantComparator;

public class WorkTimeAgentComparator implements LineServantComparator {

    @Override
    public int compare(LineServant o1, LineServant o2) {
        WorkTimeAgent workTimeAgent1 = (WorkTimeAgent) o1;
        WorkTimeAgent workTimeAgent2 = (WorkTimeAgent) o2;
        return workTimeAgent1.getDistributeTimes() - workTimeAgent2.getDistributeTimes();
    }
}
