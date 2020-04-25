package com.yuntongxun.acd.process;

import com.yuntongxun.acd.call.CallLineServantProcess;
import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.context.AbstractAcdContext;
import com.yuntongxun.acd.context.AcdContext;
import com.yuntongxun.acd.distribute.DistributeSupport;
import com.yuntongxun.acd.distribute.ServantDistributor;
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

    public void run() {
        if (null != threadPoolName && !"".equals(threadPoolName))
            Thread.currentThread().setName(threadPoolName);

        this.lineProcess();
    }

    public LineElementQueue getLineElementQueue() {
        return acdContext.getLineElementQueue();
    }

    public ServantDistributor getServantDistributor() {
        return acdContext.getServantDistributor();
    }

    public CallLineServantProcess getCallLineServantProcess() {
        return acdContext.getCallLineServantProcess();
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
                        // 置忙
                        theLineServantTemp.setActive(LineServant.NOTACTIVE);
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
        acdContext.getCallLineServantProcess().callProcess(theLineElement, theLineServant);
    }

    public AcdContext getAcdContext() {
        return acdContext;
    }
}
