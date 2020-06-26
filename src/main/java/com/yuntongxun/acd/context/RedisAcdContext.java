package com.yuntongxun.acd.context;

import com.yuntongxun.acd.Config.RedisConfig;
import org.redisson.Redisson;
import org.redisson.config.Config;

public class RedisAcdContext extends AbstractAcdContext {

    private Redisson redisson;
    private RedisConfig redisConfig;

    public RedisAcdContext(String contextId, RedisConfig redisConfig) {
        super.ContextId = contextId;
        this.redisConfig = redisConfig;
        init();
    }

    public RedisAcdContext(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
        init();
    }

    private void init() {
        if (redisConfig == null)
            throw new NullPointerException("redisConfig must be not null");

        if (!redisConfig.isCluster()) {
            Config config = new Config();
            config.useSingleServer().setAddress(redisConfig.getHosts().get(0));
            redisson = (Redisson) Redisson.create(config);
        }
    }

    public Redisson getRedisson() {
        return redisson;
    }
}
