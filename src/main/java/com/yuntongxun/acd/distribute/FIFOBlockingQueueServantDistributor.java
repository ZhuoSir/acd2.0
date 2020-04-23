package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class FIFOBlockingQueueServantDistributor extends AbstractServantDistributor {

    private BlockingQueue<LineServant> lineServantBlockingQueue;

    private Map<String, LineServant> lineServantTable;

    public FIFOBlockingQueueServantDistributor() {
        lineServantBlockingQueue = new LinkedBlockingQueue<>();
        lineServantTable = new ConcurrentHashMap<>();
    }

    @Override
    public LineServant distribute() {

        try {
            LineServant servant = lineServantBlockingQueue.take();
            lineServantTable.remove(servant.getServantId());
            return servant;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void add(LineServant lineServant) {
        lineServantBlockingQueue.add(lineServant);
        lineServantTable.put(lineServant.getServantId(), lineServant);
    }

    @Override
    public void addLineServants(Collection<LineServant> lineServants) {
        lineServantBlockingQueue.addAll(lineServants);
        for (LineServant lineServant : lineServants) {
            lineServantTable.put(lineServant.getServantId(), lineServant);
        }
    }

    @Override
    public Collection<LineServant> lineServantList() {
        return lineServantBlockingQueue;
    }

    @Override
    public void remove(String servantId) {
        LineServant lineServant = lineServantTable.get(servantId);
        if (lineServant != null) {
            lineServantBlockingQueue.remove(lineServant);
        }
        lineServantTable.remove(servantId);
    }
}
