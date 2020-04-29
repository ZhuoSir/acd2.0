package com.yuntongxun.acd.distribute.comparator;

import com.yuntongxun.acd.common.LineServant;

public class DistributeTimesComparator implements LineServantComparator {


    @Override
    public int compare(LineServant lineServant1, LineServant lineServant2) {
        if (lineServant1.getDistributeTimes() < lineServant2.getDistributeTimes()) {
            return 1;
        } else if (lineServant2.getDistributeTimes() < lineServant1.getDistributeTimes()) {
            return -1;
        }
        return 0;
    }
}
