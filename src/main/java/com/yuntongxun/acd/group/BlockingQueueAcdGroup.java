package com.yuntongxun.acd.group;

import com.yuntongxun.acd.process.BlockingQueueAcdProcessor;

public class BlockingQueueAcdGroup extends GenericAcdGroup {

    public BlockingQueueAcdGroup(String groupName) {
        super(groupName, new BlockingQueueAcdProcessor());
    }
}
