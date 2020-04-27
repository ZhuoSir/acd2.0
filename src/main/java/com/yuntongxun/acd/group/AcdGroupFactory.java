package com.yuntongxun.acd.group;

import com.yuntongxun.acd.distribute.comparator.LineServantComparator;
import com.yuntongxun.acd.proxy.ServiceProxy;

public class AcdGroupFactory {

    public static AcdGroup createAcdGroup(String groupName) {
        return null;
    }

    public static FIFOBlockingQueueAcdGroup fifoBlockingQueueAcdGroup(String groupName, ServiceProxy serviceProxy) {
        return new FIFOBlockingQueueAcdGroup(groupName, serviceProxy);
    }

    public static FIFOBlockingQueueAcdGroup fifoBlockingOfelementAndSortBlockingOfServantAcdGroup(String groupName, ServiceProxy serviceProxy, LineServantComparator lineServantComparator) {
        return new FIFOBlockingQueueAcdGroup(groupName, serviceProxy, lineServantComparator);
    }
}
