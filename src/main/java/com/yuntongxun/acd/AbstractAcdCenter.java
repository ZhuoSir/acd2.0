package com.yuntongxun.acd;

import com.yuntongxun.acd.group.AcdGroup;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractAcdCenter implements AcdCenter {

    Map<String, AcdGroup> acdGroupTable = new ConcurrentHashMap<>();

    @Override
    public void putGroup(AcdGroup acdGroup) {
        acdGroupTable.put(acdGroup.getGroupName(), acdGroup);
    }

    @Override
    public void removeGroup(String groupName) {
        acdGroupTable.remove(groupName);
    }

    @Override
    public void putGroup(Collection<AcdGroup> acdGroupCollection) {
        for (AcdGroup acdGroup : acdGroupCollection) {
            acdGroupTable.put(acdGroup.getGroupName(), acdGroup);
        }
    }

    @Override
    public void removeGroup(Collection<String> groupNames) {
        for (String acdGroupName : groupNames) {
            acdGroupTable.remove(acdGroupName);
        }
    }

    @Override
    public void putGroupAndStart(Collection<AcdGroup> acdGroupCollection) {
        for (AcdGroup acdGroup : acdGroupCollection) {
            acdGroupTable.put(acdGroup.getGroupName(), acdGroup);
            start(acdGroup.getGroupName());
        }
    }

    @Override
    public void removeGroupAndStop(Collection<String> groupNames) {
        for (String acdGroupName : groupNames) {
            acdGroupTable.remove(acdGroupName);
            stop(acdGroupName);
        }
    }

    @Override
    public void start() {
        Iterator<String> iterator = acdGroupTable.keySet().iterator();
        while (iterator.hasNext()) {
            String acdGroupName = iterator.next();
            start(acdGroupName);
        }
    }

    @Override
    public void start(String groupName) {
        AcdGroup acdGroup = acdGroupTable.get(groupName);
        if (!acdGroup.isStarted())
            acdGroup.start();
    }

    @Override
    public void stop() {
        Iterator<String> iterator = acdGroupTable.keySet().iterator();
        while (iterator.hasNext()) {
            String acdGroupName = iterator.next();
            stop(acdGroupName);
        }
    }

    @Override
    public void stop(String groupName) {
        AcdGroup acdGroup = acdGroupTable.get(groupName);
        acdGroup.close();
    }
}
