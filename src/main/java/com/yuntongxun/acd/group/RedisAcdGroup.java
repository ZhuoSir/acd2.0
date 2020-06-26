package com.yuntongxun.acd.group;

import com.yuntongxun.acd.Config.RedisConfig;
import com.yuntongxun.acd.process.RedisAcdProcessor;
import com.yuntongxun.acd.proxy.ServiceProxy;

public class RedisAcdGroup extends GenericAcdGroup {

    public RedisAcdGroup(String groupName, ServiceProxy serviceProxy, RedisConfig redisConfig) {
        super(groupName, new RedisAcdProcessor(groupName, serviceProxy, redisConfig));
    }
}
