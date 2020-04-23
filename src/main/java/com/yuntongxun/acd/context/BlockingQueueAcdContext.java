package com.yuntongxun.acd.context;

import com.yuntongxun.acd.distribute.BlockingQueueServantDistributor;
import com.yuntongxun.acd.queue.BlockingLineElementQueue;

public class BlockingQueueAcdContext extends AbstractAcdContext {

    public BlockingQueueAcdContext() {
        super.lineElementQueue       = new BlockingLineElementQueue();
        super.servantDistributor     = new BlockingQueueServantDistributor();
        super.callLineServantProcess = null;
    }
}
