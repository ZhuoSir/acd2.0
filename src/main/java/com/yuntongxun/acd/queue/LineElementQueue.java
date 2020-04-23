package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;

import java.util.Queue;

public interface LineElementQueue extends LineElementCollection {

    void add(LineElement element);

    void addPriority(LineElement element);

    void addProcessFailed(LineElement element);

    void remove(LineElement element);

    Queue<LineElement> getWaitingQueue();

    Queue<LineElement> getProcessFailedQueue();

    Queue<LineElement> getPriorityQueue();

    int waitingCount();

    void elementDistributed(LineElement lineElement, LineServant lineServant);
}
