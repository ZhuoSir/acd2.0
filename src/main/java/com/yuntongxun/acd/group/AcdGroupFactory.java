package com.yuntongxun.acd.group;

import com.yuntongxun.acd.proxy.ServiceProxy;

public class AcdGroupFactory {

    public static FIFOBlockingQueueAcdGroup fifoBlockingQueueAcdGroup(String groupName, ServiceProxy serviceProxy) {
        return new FIFOBlockingQueueAcdGroup(groupName, serviceProxy);
    }
}
