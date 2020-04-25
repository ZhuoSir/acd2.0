package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ComparaSortBlockingQueueServantDistributor extends AbstractServantDistributor {

    private ReentrantLock lock;
    private Comparator    comparator;

    private BlockingQueue<LineServant> lineServantBlockingQueue;
    private Map<String, LineServant>   lineServantTable;

    public ComparaSortBlockingQueueServantDistributor(Comparator comparator) {
        this.comparator               = comparator;
        this.lineServantBlockingQueue = new LinkedBlockingQueue<>();
        this.lock                     = new ReentrantLock();
        this.lineServantTable         = new ConcurrentHashMap<>();
    }

    @Override
    public LineServant distribute() {
        try {
            LineServant lineServant = lineServantBlockingQueue.take();
            lineServantTable.remove(lineServant.getServantId());
            return lineServant;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void add(LineServant lineServant) {
        final ReentrantLock lock = this.lock;
        try {
            lock.lock();
            lineServantBlockingQueue.add(lineServant);
            lineServantTable.put(lineServant.getServantId(), lineServant);
            // 排队逻辑
            sort();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void add(Collection<LineServant> lineServants) {
        final ReentrantLock lock = this.lock;
        try {
            lock.lock();
            lineServantBlockingQueue.addAll(lineServants);
            for (LineServant lineServant : lineServants) {
                lineServantTable.put(lineServant.getServantId(), lineServant);
            }

            // 排队逻辑
            sort();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Collection<LineServant> lineServantList() {
        return lineServantBlockingQueue;
    }

    @Override
    public void remove(String servantId) {
        final ReentrantLock lock = this.lock;
        try {
            lock.lock();
            LineServant lineServant = lineServantTable.remove(servantId);
            lineServantBlockingQueue.remove(lineServant);
        } finally {
            lock.unlock();
        }
    }

    private void sort() {
        LineServant[] servantsBeforeSort = new LineServant[lineServantBlockingQueue.size()];
        servantsBeforeSort = lineServantBlockingQueue.toArray(servantsBeforeSort);

        List<LineServant> servantListBeforeSort = Arrays.asList(servantsBeforeSort);
        Collections.sort(servantListBeforeSort, comparator);

        lineServantBlockingQueue = new LinkedBlockingQueue<>(servantListBeforeSort);
    }
}
