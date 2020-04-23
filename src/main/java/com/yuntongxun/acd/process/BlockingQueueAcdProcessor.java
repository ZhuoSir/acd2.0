package com.yuntongxun.acd.process;

import com.yuntongxun.acd.context.AcdContext;
import com.yuntongxun.acd.context.BlockingQueueAcdContext;

public class BlockingQueueAcdProcessor extends GenericAcdProcessor {

    public BlockingQueueAcdProcessor() {
        super.acdContext = new BlockingQueueAcdContext();
    }

    public BlockingQueueAcdProcessor(AcdContext acdContext) {
        super(acdContext);
    }
}
