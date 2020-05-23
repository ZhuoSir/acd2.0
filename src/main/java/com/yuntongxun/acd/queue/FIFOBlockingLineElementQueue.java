package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FIFOBlockingLineElementQueue extends AbstractLineElementQueue {

    // 等待队列
    protected BlockingQueue<LineElement> waitingQueue;
    // 处理失败队列
    protected Queue<LineElement>         processFailedQueue;
    // 优先队列
    protected Queue<LineElement>         priorityQueue;


    private ReentrantLock lock;
    private Condition     unempity;

    public FIFOBlockingLineElementQueue() {
        waitingQueue       = new LinkedBlockingQueue<>();
        priorityQueue      = new ConcurrentLinkedQueue<>();
        processFailedQueue = new ConcurrentLinkedQueue<>();
        lock               = new ReentrantLock(true);
        unempity           = lock.newCondition();
    }

    public void add(LineElement element) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            waitingQueue.add(element);
            element.setWaitingCount(waitingQueue.size() - 1);
            if (element.getWaitingCount() < 0) {
                element.setWaitingCount(0);
            }
            unempity.signal();
        } finally {
            lock.unlock();
        }
    }

    public void addPriority(LineElement element) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            priorityQueue.add(element);
            unempity.signal();
        } finally {
            lock.unlock();
        }
    }

    public void addProcessFailed(LineElement element) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            processFailedQueue.add(element);
            unempity.signal();
        } finally {
            lock.unlock();
        }
    }

    public void remove(LineElement element) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            waitingQueue.remove(element);
            priorityQueue.remove(element);
            processFailedQueue.remove(element);
        } finally {
            lock.unlock();
        }
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

    public LineElement get() throws Exception {
        final ReentrantLock lock = this.lock;
        lock.lock();
        LineElement lineElement;
        try {
            do {
                lineElement = processFailedQueue.poll();
                if (null == lineElement) {
                    lineElement = priorityQueue.poll();
                    if (null == lineElement) {
                        lineElement = waitingQueue.poll();
                    }
                }
                if (null == lineElement) {
                    unempity.await();
                }
            } while (null == lineElement);
        } finally {
            lock.unlock();
        }
        return lineElement;
    }
}
