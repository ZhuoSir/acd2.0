package com.yuntongxun.acd.process;

import com.yuntongxun.acd.context.AbstractAcdContext;
import com.yuntongxun.acd.context.AcdContext;
import com.yuntongxun.acd.context.FIFOBlockingQueueAcdContext;
import com.yuntongxun.acd.distribute.comparator.LineServantComparator;
import com.yuntongxun.acd.proxy.ServiceProxy;

public class FIFOBlockingQueueAcdProcessor extends GenericAcdProcessor {

    public FIFOBlockingQueueAcdProcessor(ServiceProxy serviceProxy) {
        super.acdContext = new FIFOBlockingQueueAcdContext(serviceProxy);
    }

    public FIFOBlockingQueueAcdProcessor(ServiceProxy serviceProxy, LineServantComparator lineServantComparator) {
        super.acdContext = new FIFOBlockingQueueAcdContext(serviceProxy, lineServantComparator);
    }

    public FIFOBlockingQueueAcdProcessor(AbstractAcdContext acdContext) {
        super(acdContext);
    }
}
