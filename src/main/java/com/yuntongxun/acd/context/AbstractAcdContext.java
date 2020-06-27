package com.yuntongxun.acd.context;

import com.yuntongxun.acd.context.listener.AfterLineAcdContextListener;
import com.yuntongxun.acd.context.listener.ExceptionAcdContextListener;
import com.yuntongxun.acd.context.listener.PreLineAcdContextListener;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAcdContext implements AcdContext {

    private boolean notifySymbol;

    private boolean callAbleSymbol;

    private boolean sychSymbol;

    public String ContextId = null;

    private int notifyThreadPoolSize = 10;

    private List<PreLineAcdContextListener> preLineAcdContextListeners;

    private List<AfterLineAcdContextListener> afterLineAcdContextListeners;

    private List<ExceptionAcdContextListener> exceptionAcdContextListeners;

    public AbstractAcdContext() {
        preLineAcdContextListeners = new ArrayList<>();
        afterLineAcdContextListeners = new ArrayList<>();
        exceptionAcdContextListeners = new ArrayList<>();
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

    public boolean isNotifySymbol() {
        return notifySymbol;
    }

    public void setNotifySymbol(boolean notifySymbol) {
        this.notifySymbol = notifySymbol;
    }

    public boolean isCallAbleSymbol() {
        return callAbleSymbol;
    }

    public void setCallAbleSymbol(boolean callAbleSymbol) {
        this.callAbleSymbol = callAbleSymbol;
    }

    public boolean isSychSymbol() {
        return sychSymbol;
    }

    public void setSychSymbol(boolean sychSymbol) {
        this.sychSymbol = sychSymbol;
    }

    public String getContextId() {
        return ContextId;
    }

    public void setContextId(String contextId) {
        ContextId = contextId;
    }

    public int getNotifyThreadPoolSize() {
        return notifyThreadPoolSize;
    }

    public void setNotifyThreadPoolSize(int notifyThreadPoolSize) {
        this.notifyThreadPoolSize = notifyThreadPoolSize;
    }

    public List<PreLineAcdContextListener> getPreLineAcdContextListeners() {
        return preLineAcdContextListeners;
    }

    public List<AfterLineAcdContextListener> getAfterLineAcdContextListeners() {
        return afterLineAcdContextListeners;
    }

    public List<ExceptionAcdContextListener> getExceptionAcdContextListeners() {
        return exceptionAcdContextListeners;
    }

    public boolean addPreLineListener(PreLineAcdContextListener preLineAcdContextListener) {
        if (preLineAcdContextListeners == null) {
            preLineAcdContextListeners = new ArrayList<>();
        }
        return preLineAcdContextListeners.add(preLineAcdContextListener);
    }

    public boolean addAfterLineListener(AfterLineAcdContextListener afterLineAcdContextListener) {
        if (afterLineAcdContextListeners == null) {
            afterLineAcdContextListeners = new ArrayList<>();
        }
        return afterLineAcdContextListeners.add(afterLineAcdContextListener);
    }

    public boolean addExeceptionListeners(ExceptionAcdContextListener exceptionAcdContextListener) {
        if (exceptionAcdContextListeners == null) {
            exceptionAcdContextListeners = new ArrayList<>();
        }
        return exceptionAcdContextListeners.add(exceptionAcdContextListener);
    }
}
