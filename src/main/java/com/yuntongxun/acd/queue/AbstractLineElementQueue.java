package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.common.LineDistributePart;
import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.queue.bean.QueueNotification;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractLineElementQueue implements LineElementQueue {

    protected Map<String, LineDistributePart> distributePartTable = new ConcurrentHashMap<>();

    private ExecutorService taskPool = Executors.newCachedThreadPool();

    private QueueNotifyProxy queueNotifyProxy;

    @Override
    public void elementDistributed(LineElement lineElement, LineServant lineServant) {
        lineServant.setActive(LineServant.NOTACTIVE);
        lineServant.setDistributeTimes(lineServant.getDistributeTimes() + 1);
        LineDistributePart lineDistributePart = new LineDistributePart(lineElement, lineServant);
        String distributeId = lineElement.index() + "-" + lineServant.getServantId();
        distributePartTable.put(distributeId, lineDistributePart);

        distributeNotify(lineElement, lineServant);
        queueAdjust();
    }

    private void distributeNotify(LineElement lineElement, LineServant lineServant) {
        if (null == queueNotifyProxy) return;
        QueueNotification queueNotification = new QueueNotification(lineElement, 0, new Date(), 1);
        queueNotification.setDistributedServant(lineServant);
        queueNotifyProxy.sendQueueNotification(queueNotification);
    }

    private void queueAdjust() {
        Queue<LineElement> waitingQueue = getWaitingQueue();
        Iterator<LineElement> iterator = waitingQueue.iterator();
        taskPool.submit(new Runnable() {
            @Override
            public void run() {
                int preCount = 0;
                while (iterator.hasNext()) {
                    LineElement lineElement = iterator.next();
                    lineElement.setWaitingCount(preCount);
                    if (queueNotifyProxy != null) {
                        QueueNotification queueNotification = new QueueNotification(lineElement, preCount, new Date(), 0);
                        taskPool.submit(new Runnable() {
                            @Override
                            public void run() {
                                queueNotifyProxy.sendQueueNotification(queueNotification);
                            }
                        });
                    }
                    preCount++;
                }
            }
        });
    }

    public void setQueueNotifyProxy(QueueNotifyProxy queueNotifyProxy) {
        this.queueNotifyProxy = queueNotifyProxy;
    }
}
