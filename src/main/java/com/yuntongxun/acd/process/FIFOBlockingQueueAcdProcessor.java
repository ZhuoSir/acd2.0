package com.yuntongxun.acd.process;

import com.yuntongxun.acd.context.GenericAcdContext;
import com.yuntongxun.acd.distribute.ComparaSortBlockingListServantDistributor;
import com.yuntongxun.acd.distribute.FIFOBlockingQueueServantDistributor;
import com.yuntongxun.acd.distribute.comparator.LineServantComparator;
import com.yuntongxun.acd.proxy.ServiceProxy;
import com.yuntongxun.acd.queue.FIFOBlockingLineElementQueue;

public class FIFOBlockingQueueAcdProcessor extends GenericAcdProcessor {

    public FIFOBlockingQueueAcdProcessor(String contextId, ServiceProxy serviceProxy) {
        super.acdContext = new GenericAcdContext(contextId);
        super.lineElementQueue = new FIFOBlockingLineElementQueue(acdContext);
        super.lineElementQueue.setQueueNotifyProxy(serviceProxy);
        super.servantDistributor = new FIFOBlockingQueueServantDistributor(acdContext);
        super.callLineServantProcess = null;
    }

    public FIFOBlockingQueueAcdProcessor(String contextId, ServiceProxy serviceProxy, LineServantComparator lineServantComparator) {
        super.acdContext = new GenericAcdContext(contextId);
        super.lineElementQueue = new FIFOBlockingLineElementQueue(acdContext);
        super.lineElementQueue.setQueueNotifyProxy(serviceProxy);
        super.servantDistributor = new ComparaSortBlockingListServantDistributor(acdContext, lineServantComparator);
        super.callLineServantProcess = null;
    }

}
