package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingLineElementQueue extends AbstractLineElementQueue {

    // 等待队列
    protected BlockingQueue<LineElement> waitingQueue;
    // 处理失败队列
    protected Queue<LineElement> processFailedQueue;
    // 优先队列
    protected Queue<LineElement> priorityQueue;


    public BlockingLineElementQueue() {
        waitingQueue        = new LinkedBlockingQueue<>();
        priorityQueue       = new ConcurrentLinkedQueue<>();
        processFailedQueue  = new ConcurrentLinkedQueue<>();
    }

    public void add(LineElement element) {
        waitingQueue.add(element);
        element.setWaitingCount(waitingQueue.size() - 1);
        if (element.getWaitingCount() < 0) {
            element.setWaitingCount(0);
        }
    }

    public void addPriority(LineElement element) {
        priorityQueue.add(element);
    }

    public void addProcessFailed(LineElement element) {
        processFailedQueue.add(element);
    }

    public void remove(LineElement element) {
        waitingQueue.remove(element);
        priorityQueue.remove(element);
        processFailedQueue.remove(element);
    }

    public Queue<LineElement> getWaitingQueue() {
        return waitingQueue;
    }

    public Queue<LineElement> getProcessFailedQueue() {
        return processFailedQueue;
    }

    public Queue<LineElement> getPriorityQueue() {
        return priorityQueue;
    }

    public int waitingCount() {
        return waitingQueue.size();
    }

    public LineElement get() {
        LineElement lineElement = processFailedQueue.poll();
        if (null == lineElement) {
            lineElement = priorityQueue.poll();
            if (null == lineElement) {
                lineElement = waitingQueue.poll();
            }
        }
        return lineElement;
    }
}
