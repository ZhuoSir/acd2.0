package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.context.AbstractAcdContext;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class FIFOBlockingQueueServantDistributor extends AbstractServantDistributor {

    private BlockingQueue<LineServant> lineServantBlockingQueue;

    private Map<String, LineServant> lineServantTable;

    public FIFOBlockingQueueServantDistributor() {
        init();
    }

    public FIFOBlockingQueueServantDistributor(AbstractAcdContext acdContext) {
        super(acdContext);
        init();
    }

    private void init() {
        lineServantBlockingQueue = new LinkedBlockingQueue<>();
        lineServantTable = new ConcurrentHashMap<>();
    }

    @Override
    public LineServant distribute() throws Exception {

        try {
            LineServant servant = lineServantBlockingQueue.take();
            lineServantTable.remove(servant.getServantId());
            return servant;
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public void add(LineServant lineServant) throws Exception {
        lineServantBlockingQueue.add(lineServant);
        lineServantTable.put(lineServant.getServantId(), lineServant);
    }

    @Override
    public void add(Collection<LineServant> lineServants) throws Exception {
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

    @Override
    public LineServant lineServantInfo(String servantId) {
        return lineServantTable.get(servantId);
    }
}
