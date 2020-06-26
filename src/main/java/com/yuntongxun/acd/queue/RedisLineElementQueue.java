package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.context.RedisAcdContext;
import org.redisson.Redisson;
import org.redisson.api.RQueue;

import java.util.Queue;

public class RedisLineElementQueue extends AbstractLineElementQueue {

    Redisson redisson;

    private RQueue<LineElement> rWatingQueue;
    private RQueue<LineElement> rVIPQueue;
    private RQueue<LineElement> rFailedQueue;

    private String contextID;

    public RedisLineElementQueue(RedisAcdContext acdContext) {
        super(acdContext);

        contextID = acdContext.ContextId;

        redisson = acdContext.getRedisson();

        rWatingQueue = redisson.getQueue(contextID + "-queue");
        rVIPQueue    = redisson.getQueue(contextID + "-vip-queue");
        rFailedQueue = redisson.getQueue(contextID + "-failed-queue");
    }

    @Override
    public void add(LineElement element) {
        try {
            rWatingQueue.offer(element);
            element.setWaitingCount(waitingCount() - 1);
            if (element.getWaitingCount() < 0) {
                element.setWaitingCount(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void addPriority(LineElement element) {
        try {
            rVIPQueue.offer(element);
            element.setWaitingCount(rVIPQueue.size() - 1);
            if (element.getWaitingCount() < 0) {
                element.setWaitingCount(0);
            }
        } finally {
        }
    }

    @Override
    public void addProcessFailed(LineElement element) {
        try {
//            rLock.lock();
            rFailedQueue.offer(element);
            element.setWaitingCount(rFailedQueue.size() - 1);
            if (element.getWaitingCount() < 0) {
                element.setWaitingCount(0);
            }
        } finally {
//            rLock.unlock();
        }
    }

    @Override
    public void remove(LineElement element) {
        rWatingQueue.remove(element);
        rVIPQueue.remove(element);
        rFailedQueue.remove(element);
    }

    @Override
    public Queue<LineElement> getWaitingQueue() {
        reGetWaitingQueue();
        System.out.println(rWatingQueue);
        return rWatingQueue;
    }

    private void reGetWaitingQueue() {
        rWatingQueue = redisson.getQueue(contextID + "-queue");
    }

    @Override
    public Queue<LineElement> getProcessFailedQueue() {
        return rFailedQueue;
    }

    @Override
    public Queue<LineElement> getPriorityQueue() {
        return rVIPQueue;
    }

    @Override
    public int waitingCount() {
        return rWatingQueue.size();
    }

    @Override
    public LineElement get() throws Exception {

        LineElement lineElement = null;
        try {
            do {
                lineElement = rVIPQueue.poll();
                if (null == lineElement) {
                    lineElement = rFailedQueue.poll();
                    if (lineElement == null) {
                        lineElement = rWatingQueue.poll();
                    }
                }

                if (lineElement == null)
                    Thread.sleep(1000);
            } while (lineElement == null);

        } finally {
        }
        return lineElement;
    }
}
