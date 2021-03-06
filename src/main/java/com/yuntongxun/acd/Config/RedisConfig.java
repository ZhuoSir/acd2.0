package com.yuntongxun.acd.Config;

import java.util.List;

public class RedisConfig {

    private boolean isCluster = false;

    private String password;

    private List<String> hosts;

    public List<String> getHosts() {
        return hosts;
    }

    public boolean isCluster() {
        return isCluster;
    }

    public void setCluster(boolean cluster) {
        isCluster = cluster;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
