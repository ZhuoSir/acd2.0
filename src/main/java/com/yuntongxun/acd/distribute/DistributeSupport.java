package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;

import java.util.Collection;

public interface DistributeSupport {

    void addLineServant(LineServant lineServant);

    void addLineServants(Collection<LineServant> lineServants);

    Collection<LineServant> lineServantList();

    void remove(String servantId);
}
