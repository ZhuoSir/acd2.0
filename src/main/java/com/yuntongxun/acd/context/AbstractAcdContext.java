package com.yuntongxun.acd.context;

import com.yuntongxun.acd.call.CallLineServantProcess;
import com.yuntongxun.acd.distribute.ServantDistributor;
import com.yuntongxun.acd.queue.LineElementQueue;

public abstract class AbstractAcdContext implements AcdContext {

    protected LineElementQueue lineElementQueue;

    protected ServantDistributor servantDistributor;

    protected CallLineServantProcess callLineServantProcess;

    private boolean notifySymbol;

    private boolean callAbleSymbol;

    private boolean sychSymbol;

    private CustomerProxy customerProxy;

    public void setLineElementQueue(LineElementQueue lineElementQueue) {
        this.lineElementQueue = lineElementQueue;
    }

    public void setServantDistributor(ServantDistributor servantDistributor) {
        this.servantDistributor = servantDistributor;
    }

    public void setCallLineServantProcess(CallLineServantProcess callLineServantProcess) {
        this.callLineServantProcess = callLineServantProcess;
    }

    @Override
    public LineElementQueue getLineElementQueue() {
        return lineElementQueue;
    }

    @Override
    public ServantDistributor getServantDistributor() {
        return servantDistributor;
    }

    @Override
    public CallLineServantProcess getCallLineServantProcess() {
        return callLineServantProcess;
    }

    @Override
    public boolean isNotify() {
        return notifySymbol;
    }

    @Override
    public boolean isCallable() {
        return callAbleSymbol;
    }

    @Override
    public boolean isSycn() {
        return sychSymbol;
    }

    public void setNotifySymbol(boolean notifySymbol) {
        this.notifySymbol = notifySymbol;
    }

    public void setCallAbleSymbol(boolean callAbleSymbol) {
        this.callAbleSymbol = callAbleSymbol;
    }

    public void setSychSymbol(boolean sychSymbol) {
        this.sychSymbol = sychSymbol;
    }

    public void setCustomerProxy(CustomerProxy customerProxy) {
        this.customerProxy = customerProxy;
    }
}
