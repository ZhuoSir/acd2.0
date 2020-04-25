package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;

import java.util.Collection;


public interface ServantDistributor {

    LineServant distribute();

    void add(LineServant lineServant);

    void add(Collection<LineServant> lineServants);

    Collection<LineServant> lineServantList();

    void remove(String servantId);
}
