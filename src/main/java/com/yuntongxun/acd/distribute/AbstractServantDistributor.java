package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.context.AbstractAcdContext;

public abstract class AbstractServantDistributor implements ServantDistributor {

    private AbstractAcdContext acdContext;

    public AbstractServantDistributor() {
    }

    public AbstractServantDistributor(AbstractAcdContext acdContext) {
        this.acdContext = acdContext;
    }

    public void setAcdContext(AbstractAcdContext acdContext) {
        this.acdContext = acdContext;
    }

    public AbstractAcdContext getAcdContext() {
        return acdContext;
    }
}
