package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.common.LineElement;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingLineElementQueue extends AbstractLineElementQueue {


    private PriorityBlockingQueue<LineElement> lineElements;

    // 只做存储，不做排队逻辑
    private Queue<LineElement> failedQueue;
    private Queue<LineElement> priorityQueue;

    public PriorityBlockingLineElementQueue() {
        this.lineElements = new PriorityBlockingQueue<>();
        this.failedQueue  = new LinkedList<>();
        this.priorityQueue  = new LinkedList<>();
    }

    @Override
    public void add(LineElement element) {
        lineElements.add(element);
    }

    @Override
    public void addPriority(LineElement element) {
        element.setPriority(2);
        lineElements.add(element);
        priorityQueue.add(element);
    }

    @Override
    public void addProcessFailed(LineElement element) {
        element.setPriority(1);
        lineElements.add(element);
        failedQueue.add(element);
    }

    @Override
    public void remove(LineElement element) {
        lineElements.remove(element);
        failedQueue.remove(element);
        priorityQueue.remove(element);
    }

    @Override
    public Queue<LineElement> getWaitingQueue() {
        return lineElements;
    }

    @Override
    public Queue<LineElement> getProcessFailedQueue() {
        return failedQueue;
    }

    @Override
    public Queue<LineElement> getPriorityQueue() {
        return priorityQueue;
    }

    @Override
    public int waitingCount() {
        return priorityQueue.size();
    }

    @Override
    public LineElement get() throws InterruptedException {
        return lineElements.take();
    }
}
