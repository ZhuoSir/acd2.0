package com.yuntongxun.acd.process;

import com.yuntongxun.acd.Config.RedisConfig;
import com.yuntongxun.acd.context.RedisAcdContext;
import com.yuntongxun.acd.distribute.RedisComparaSortQueueServantDistributor;
import com.yuntongxun.acd.distribute.RedisFIFOBlockingQueueServantDistributor;
import com.yuntongxun.acd.distribute.comparator.LineServantComparator;
import com.yuntongxun.acd.proxy.ServiceProxy;
import com.yuntongxun.acd.queue.RedisLineElementQueue;

public class RedisAcdProcessor extends GenericAcdProcessor {

    public RedisAcdProcessor(String contextId, ServiceProxy serviceProxy, RedisConfig redisConfig) {
        final RedisAcdContext redisAcdContext = new RedisAcdContext(contextId, redisConfig);
        super.acdContext = redisAcdContext;
        super.lineElementQueue = new RedisLineElementQueue(redisAcdContext);
        if (serviceProxy != null)
            super.lineElementQueue.setQueueNotifyProxy(serviceProxy);
        super.servantDistributor = new RedisFIFOBlockingQueueServantDistributor(redisAcdContext);
        super.callLineServantProcess = null;
    }

    public RedisAcdProcessor(String contextId, ServiceProxy serviceProxy, RedisConfig redisConfig, LineServantComparator lineServantComparator) {
        final RedisAcdContext redisAcdContext = new RedisAcdContext(contextId, redisConfig);
        super.acdContext = redisAcdContext;
        super.lineElementQueue = new RedisLineElementQueue(redisAcdContext);
        if (serviceProxy != null)
            super.lineElementQueue.setQueueNotifyProxy(serviceProxy);
        super.servantDistributor = new RedisComparaSortQueueServantDistributor(redisAcdContext, lineServantComparator);
        super.callLineServantProcess = null;
    }
}
