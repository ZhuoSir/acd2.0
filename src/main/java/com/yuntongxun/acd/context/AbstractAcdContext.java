package com.yuntongxun.acd.context;

public abstract class AbstractAcdContext implements AcdContext {

//    protected AbstractLineElementQueue lineElementQueue;
//
//    protected AbstractServantDistributor servantDistributor;
//
//    protected CallLineServantProcess callLineServantProcess;

    private boolean notifySymbol;

    private boolean callAbleSymbol;

    private boolean sychSymbol;

    public String ContextId = null;

    private int notifyThreadPoolSize = 10;

    public AbstractAcdContext() {
    }

//    public AbstractAcdContext(AbstractLineElementQueue lineElementQueue,
//                              AbstractServantDistributor servantDistributor,
//                              CallLineServantProcess callLineServantProcess,
//                              ServiceProxy serviceProxy) {
//        this.lineElementQueue = lineElementQueue;
//        this.servantDistributor = servantDistributor;
//        this.callLineServantProcess = callLineServantProcess;
//        this.serviceProxy = serviceProxy;
//
//        init();
//    }
//
//    private void init() {
//        if (serviceProxy != null) {
//            if (this.lineElementQueue != null) {
//                this.lineElementQueue.setQueueNotifyProxy(serviceProxy);
//            }
//        }
//    }

//    private ServiceProxy serviceProxy;

//    public void setLineElementQueue(AbstractLineElementQueue lineElementQueue) {
//        this.lineElementQueue = lineElementQueue;
//    }
//
//    public void setServantDistributor(AbstractServantDistributor servantDistributor) {
//        this.servantDistributor = servantDistributor;
//    }
//
//    public void setCallLineServantProcess(CallLineServantProcess callLineServantProcess) {
//        this.callLineServantProcess = callLineServantProcess;
//    }

//    @Override
//    public LineElementQueue getLineElementQueue() {
//        return lineElementQueue;
//    }
//
//    @Override
//    public ServantDistributor getServantDistributor() {
//        return servantDistributor;
//    }
//
//    @Override
//    public CallLineServantProcess getCallLineServantProcess() {
//        return callLineServantProcess;
//    }
//
//    @Override
//    public ServiceProxy getServiceProxy() {
//        return serviceProxy;
//    }

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

    public int getNotifyThreadPoolSize() {
        return notifyThreadPoolSize;
    }
}
