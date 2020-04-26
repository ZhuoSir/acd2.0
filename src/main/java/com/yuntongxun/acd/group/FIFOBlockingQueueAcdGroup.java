package com.yuntongxun.acd.group;

import com.yuntongxun.acd.distribute.comparator.LineServantComparator;
import com.yuntongxun.acd.process.FIFOBlockingQueueAcdProcessor;
import com.yuntongxun.acd.proxy.ServiceProxy;

public class FIFOBlockingQueueAcdGroup extends GenericAcdGroup {

    public FIFOBlockingQueueAcdGroup(String groupName, ServiceProxy serviceProxy) {
        super(groupName, new FIFOBlockingQueueAcdProcessor(serviceProxy));
    }

    public FIFOBlockingQueueAcdGroup(String groupName, ServiceProxy serviceProxy, LineServantComparator lineServantComparator) {
        super(groupName, new FIFOBlockingQueueAcdProcessor(serviceProxy, lineServantComparator));
    }
}
