package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.context.RedisAcdContext;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RMap;

import java.util.Collection;

public class RedisFIFOBlockingQueueServantDistributor extends AbstractServantDistributor {

    private Redisson redisson;

    private RBlockingQueue<LineServant> servantRBlockingQueue;
    private RMap<String, LineServant> lineServantRMap;

    public RedisFIFOBlockingQueueServantDistributor(RedisAcdContext acdContext) {
        super(acdContext);

        redisson                = acdContext.getRedisson();
        final String contextId  = acdContext.ContextId;
        servantRBlockingQueue   = redisson.getBlockingQueue(contextId + "-servantqueue");
        lineServantRMap         = redisson.getMap(contextId + "-servantmap");
    }

    @Override
    public LineServant distribute() {

        try {
            LineServant lineServant = servantRBlockingQueue.take();
            lineServantRMap.remove(lineServant.getServantId());
            return lineServant;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void add(LineServant lineServant) {

        if (lineServant == null)
            return;

        boolean r = servantRBlockingQueue.add(lineServant);
        if (r) {
            lineServantRMap.put(lineServant.getServantId(), lineServant);
        }
    }

    @Override
    public void add(Collection<LineServant> lineServants) {

        if (lineServants == null || lineServants.isEmpty())
            return;

        boolean r = servantRBlockingQueue.addAll(lineServants);
        if (r) {
            for (LineServant lineServant : lineServants) {
                lineServantRMap.put(lineServant.getServantId(), lineServant);
            }
        }
    }

    @Override
    public Collection<LineServant> lineServantList() {
        return servantRBlockingQueue;
    }

    @Override
    public void remove(String servantId) {

        if (servantId == null)
            return;

        LineServant lineServant = lineServantRMap.get(servantId);
        if (null != lineServant) {
            servantRBlockingQueue.remove(lineServant);
        }
    }

    @Override
    public LineServant lineServantInfo(String servantId) {

        if (servantId == null)
            return null;

        return lineServantRMap.get(servantId);
    }
}
