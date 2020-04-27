package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.distribute.comparator.LineServantComparator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ComparaSortBlockingListServantDistributor extends AbstractServantDistributor {

    private ReentrantLock         lock;
    private LineServantComparator comparator;
    private Condition             unEmpty;

    private List<LineServant> lineServantList;
    private Map<String, LineServant>   lineServantTable;


    public ComparaSortBlockingListServantDistributor(LineServantComparator comparator) {
        this.comparator        = comparator;
        this.lineServantList   = new LinkedList<>();
        this.lock              = new ReentrantLock();
        this.lineServantTable  = new ConcurrentHashMap<>();
        this.unEmpty           = this.lock.newCondition();
    }

    @Override
    public LineServant distribute() {
        final ReentrantLock lock = this.lock;
        LineServant lineServant = null;
        try {
            lock.lock();
            do {
                if (this.lineServantList.isEmpty()) {
                    unEmpty.await();
                }
                lineServant = this.lineServantList.remove(0);
            } while (lineServant == null);

            lineServantTable.remove(lineServant.getServantId());
            return lineServant;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public void add(LineServant lineServant) {
        final ReentrantLock lock = this.lock;
        try {
            lock.lock();
            lineServantList.add(lineServant);
            lineServantTable.put(lineServant.getServantId(), lineServant);
            sort();
            unEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void add(Collection<LineServant> lineServants) {
        final ReentrantLock lock = this.lock;
        try {
            lock.lock();
            lineServantList.addAll(lineServants);
            for (LineServant lineServant : lineServants) {
                lineServantTable.put(lineServant.getServantId(), lineServant);
            }

            sort();
            unEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Collection<LineServant> lineServantList() {
        return lineServantList;
    }

    @Override
    public void remove(String servantId) {
        final ReentrantLock lock = this.lock;
        try {
            lock.lock();
            LineServant lineServant = lineServantTable.remove(servantId);
            lineServantList.remove(lineServant);
        } finally {
            lock.unlock();
        }
    }

    private void sort() {
        if (!lineServantList.isEmpty()) {
            Collections.sort(lineServantList, comparator);
        }
    }

    @Override
    public LineServant lineServantInfo(String servantId) {
        return lineServantTable.get(servantId);
    }
}
