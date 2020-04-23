package com.yuntongxun.acd.group;

public class AcdGroupFactory {

    public static BlockingQueueAcdGroup newBlockingQueueAcdGroup(String groupName) {
        return new BlockingQueueAcdGroup(groupName);
    }
}
