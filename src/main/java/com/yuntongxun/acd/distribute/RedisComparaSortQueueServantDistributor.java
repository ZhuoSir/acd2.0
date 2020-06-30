package com.yuntongxun.acd.distribute;

import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.context.RedisAcdContext;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RMap;

import java.util.Collection;
import java.util.Comparator;

public class RedisComparaSortQueueServantDistributor extends AbstractServantDistributor {

    private Redisson redisson;

    private RList<LineServant> lineServantRList;
    private RMap<String, LineServant> lineServantRMap;

    private Comparator<LineServant> comparator;

    public RedisComparaSortQueueServantDistributor(RedisAcdContext acdContext, Comparator<LineServant> comparator) {
        super(acdContext);

        this.redisson = acdContext.getRedisson();
        lineServantRList = this.redisson.getList(acdContext.getContextId() + "-servant-list");
        lineServantRMap = this.redisson.getMap(acdContext.getContextId() + "-servant-map");
        this.comparator = comparator;
    }

    @Override
    public LineServant distribute() throws Exception {

        LineServant lineServant = null;
        do {
            lineServant = lineServantRList.remove(0);
            if (lineServant == null) {
                Thread.sleep(1000);
            } else {
                lineServantRMap.remove(lineServant.getServantId());
            }
        } while (lineServant == null);

        return lineServant;
    }

    @Override
    public void add(LineServant lineServant) throws Exception {
        if (lineServant == null)
            return;

        boolean r = lineServantRList.add(lineServant);
        if (r) {
            lineServantRMap.put(lineServant.getServantId(), lineServant);
            sort();
        }
    }

    @Override
    public void add(Collection<LineServant> lineServants) throws Exception {
        lineServantRList.addAll(lineServants);
        for (LineServant lineServant : lineServants) {
            lineServantRMap.put(lineServant.getServantId(), lineServant);
        }
        sort();
    }

    @Override
    public Collection<LineServant> lineServantList() {
        return lineServantRList;
    }

    private void sort() {
        if (lineServantRList.size() > 1) {
            lineServantRList.sort(comparator);
        }
    }

    @Override
    public void remove(String servantId) {
        LineServant lineServant = lineServantRMap.get(servantId);
        if (lineServant != null) {
            lineServantRList.remove(lineServant);
        }
    }

    @Override
    public LineServant lineServantInfo(String servantId) {
        return lineServantRMap.get(servantId);
    }
}
