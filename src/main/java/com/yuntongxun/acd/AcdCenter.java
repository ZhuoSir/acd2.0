package com.yuntongxun.acd;

import com.yuntongxun.acd.context.listener.AcdContextListener;
import com.yuntongxun.acd.group.AcdGroup;

import java.util.Collection;

public interface AcdCenter {

    void putGroup(AcdGroup acdGroup);

    void putGroup(Collection<AcdGroup> acdGroupCollection);

    void removeGroup(Collection<String> groupNames);

    void removeGroup(String groupName);

    AcdGroup getGroup(String groupName);

    void putGroupAndStart(Collection<AcdGroup> acdGroupCollection);

    void removeGroupAndStop(Collection<String> groupNames);

    void addAcdContextListener(AcdContextListener acdContextListener);

    void start();

    void stop();

    void start(String groupName);

    void stop(String groupName);
}
