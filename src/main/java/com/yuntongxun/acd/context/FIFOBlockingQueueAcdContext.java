package com.yuntongxun.acd.context;

import com.yuntongxun.acd.distribute.ComparaSortBlockingListServantDistributor;
import com.yuntongxun.acd.distribute.FIFOBlockingQueueServantDistributor;
import com.yuntongxun.acd.distribute.comparator.LineServantComparator;
import com.yuntongxun.acd.proxy.ServiceProxy;
import com.yuntongxun.acd.queue.FIFOBlockingLineElementQueue;

public class FIFOBlockingQueueAcdContext extends AbstractAcdContext {

    public FIFOBlockingQueueAcdContext(ServiceProxy serviceProxy) {
        super(new FIFOBlockingLineElementQueue(), new FIFOBlockingQueueServantDistributor(), null, serviceProxy);
    }

    public FIFOBlockingQueueAcdContext(ServiceProxy serviceProxy, LineServantComparator comparator) {
        super(new FIFOBlockingLineElementQueue(), new ComparaSortBlockingListServantDistributor(comparator), null, serviceProxy);
    }
}
