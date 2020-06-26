package com.yuntongxun.acd.process;

import com.yuntongxun.acd.call.CallLineServantProcess;
import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.context.AbstractAcdContext;
import com.yuntongxun.acd.context.AcdContext;
import com.yuntongxun.acd.distribute.AbstractServantDistributor;
import com.yuntongxun.acd.distribute.DistributeSupport;
import com.yuntongxun.acd.distribute.ServantDistributor;
import com.yuntongxun.acd.queue.AbstractLineElementQueue;
import com.yuntongxun.acd.queue.LineElementQueue;
import com.yuntongxun.acd.queue.QueueSupport;

import java.io.Closeable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AcdProcessor implements AcdProcess, QueueSupport, DistributeSupport, Runnable {

    private ExecutorService threadPool;
    private String threadPoolName;

    protected AbstractLineElementQueue lineElementQueue;
    protected AbstractServantDistributor servantDistributor;
    protected CallLineServantProcess callLineServantProcess;

    protected AbstractAcdContext acdContext;

    public AcdProcessor() {
    }

    public AcdProcessor(AbstractAcdContext acdContext) {
        this.acdContext = acdContext;
    }

    public void setThreadPool(String threadPoolName, ExecutorService threadPool) {
        this.threadPool = threadPool;
        this.threadPoolName = threadPoolName;
    }

    public void setLineElementQueue(AbstractLineElementQueue lineElementQueue) {
        this.lineElementQueue = lineElementQueue;
    }

    public void setServantDistributor(AbstractServantDistributor servantDistributor) {
        this.servantDistributor = servantDistributor;
    }

    public void setCallLineServantProcess(CallLineServantProcess callLineServantProcess) {
        this.callLineServantProcess = callLineServantProcess;
    }

    public void run() {
        if (null != threadPoolName && !"".equals(threadPoolName))
            Thread.currentThread().setName(threadPoolName);

        this.lineProcess();
    }

    public LineElementQueue getLineElementQueue() {
        return lineElementQueue;
    }

    public ServantDistributor getServantDistributor() {
        return servantDistributor;
    }

    public CallLineServantProcess getCallLineServantProcess() {
        return callLineServantProcess;
    }

    @Override
    public void lineProcess() {
        LineElementQueue        lineElementQueue        = getLineElementQueue();
        ServantDistributor      servantDistributor      = getServantDistributor();
        CallLineServantProcess  callLineServantProcess  = getCallLineServantProcess();

        LineServant theLineServant = null;
        for (;;) {
            if (Objects.isNull(lineElementQueue)
                    || Objects.isNull(servantDistributor)
                    || (acdContext.isCallable() && Objects.isNull(callLineServantProcess))) {
                break;
            }

            if (theLineServant == null || !theLineServant.isActive()) {
                theLineServant = servantDistributor.distribute();
            }

            final LineServant theLineServantTemp = theLineServant;
            if (null != theLineServantTemp && theLineServantTemp.isActive()) {
                try {
                    final LineElement theLineElement = lineElementQueue.get();
                    if (theLineElement != null) {
                        lineElementQueue.elementDistributed(theLineElement, theLineServantTemp);
                        if (acdContext.isCallable()) {
                            if (acdContext.isSycn()) {
                                callProcess(theLineElement, theLineServantTemp);
                            } else {
                                if (null == threadPool) {
                                    threadPool = Executors.newCachedThreadPool();
                                }
                                threadPool.submit(new Runnable() {
                                    public void run() {
                                        callProcess(theLineElement, theLineServantTemp);
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void callProcess(LineElement theLineElement, LineServant theLineServant) {
        callLineServantProcess.callProcess(theLineElement, theLineServant);
    }

    public AcdContext getAcdContext() {
        return acdContext;
    }
}
