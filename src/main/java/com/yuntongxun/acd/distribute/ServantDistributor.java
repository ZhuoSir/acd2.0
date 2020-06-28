package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;

import java.util.Collection;


public interface ServantDistributor {

    LineServant distribute() throws Exception;

    void add(LineServant lineServant) throws Exception;

    void add(Collection<LineServant> lineServants) throws Exception;

    Collection<LineServant> lineServantList();

    void remove(String servantId);

    LineServant lineServantInfo(String servantId);
}
