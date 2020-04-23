package com.yuntongxun.acd.group;

import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.distribute.DistributeSupport;
import com.yuntongxun.acd.process.AcdProcessor;
import com.yuntongxun.acd.queue.QueueSupport;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public abstract class AcdGroup implements QueueSupport, DistributeSupport, ThreadFactory, AcdGroupBoostrap {

    private ExecutorService threadPool;

    private String groupName;

    private AcdProcessor acdProcessor;

    private Thread processThread;

    public AcdGroup(AcdProcessor acdProcessor) {
        this("default", acdProcessor);
    }

    public AcdGroup(String groupName, AcdProcessor acdProcessor) {
        this.acdProcessor = acdProcessor;
        this.groupName = groupName;
        threadPool = Executors.newCachedThreadPool(this);
        acdProcessor.setThreadPool(groupName + "-lineProcess", threadPool);
        processThread = new Thread(acdProcessor);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void line(LineElement lineElement) {
        acdProcessor.line(lineElement);
    }

    public void lineFailed(LineElement lineElement) {
        acdProcessor.lineFailed(lineElement);
    }

    public void cancelLine(LineElement lineElement) {
        acdProcessor.cancelLine(lineElement);
    }

    public void linePriority(LineElement lineElement) {
        acdProcessor.linePriority(lineElement);
    }

    public void lineProcess() {
        threadPool.submit(acdProcessor);
    }

    @Override
    public void addLineServant(LineServant lineServant) {
        acdProcessor.addLineServant(lineServant);
    }

    @Override
    public void addLineServants(Collection<LineServant> lineServants) {
        acdProcessor.addLineServants(lineServants);
    }

    @Override
    public Collection<LineServant> lineServantList() {
        return acdProcessor.lineServantList();
    }

    @Override
    public void remove(String servantId) {
        acdProcessor.remove(servantId);
    }

    public Thread newThread(Runnable r) {
        return new Thread(groupName+"-threadPool");
    }

    @Override
    public void start() {
        processThread.start();
    }

    @Override
    public boolean isStarted() {
        return processThread.isAlive();
    }

    @Override
    public void close() {
        if (processThread.isInterrupted())
            processThread.interrupt();
    }
}
