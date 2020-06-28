package com.yuntongxun.acd.process;

import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.context.AbstractAcdContext;
import com.yuntongxun.acd.context.AcdContext;
import com.yuntongxun.acd.group.AcdGroup;

import java.util.Collection;

public class GenericAcdProcessor extends AcdProcessor {

    public GenericAcdProcessor() {
    }

    public GenericAcdProcessor(AbstractAcdContext acdContext) {
        super(acdContext);
    }

    @Override
    public void line(LineElement lineElement) {
        getLineElementQueue().add(lineElement);
    }

    @Override
    public void lineFailed(LineElement lineElement) {
        getLineElementQueue().addProcessFailed(lineElement);
    }

    @Override
    public void cancelLine(LineElement lineElement) {
        getLineElementQueue().remove(lineElement);
    }

    @Override
    public void linePriority(LineElement lineElement) {
        getLineElementQueue().addPriority(lineElement);
    }

    @Override
    public Collection<LineElement> elements() {
        return getLineElementQueue().getWaitingQueue();
    }

    @Override
    public void addLineServant(LineServant lineServant) {
        try {
            getServantDistributor().add(lineServant);
        } catch (Exception e) {
            getAcdContext().execeptionWork(e);
        }
    }

    @Override
    public void addLineServants(Collection<LineServant> lineServants) {
        try {
            getServantDistributor().add(lineServants);
        } catch (Exception e) {
            getAcdContext().execeptionWork(e);
        }
    }

    @Override
    public Collection<LineServant> lineServantList() {
        return getServantDistributor().lineServantList();
    }

    @Override
    public void remove(String servantId) {
        getServantDistributor().remove(servantId);
    }
}
