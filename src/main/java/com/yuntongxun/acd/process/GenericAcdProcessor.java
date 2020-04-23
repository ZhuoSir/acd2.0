package com.yuntongxun.acd.process;

import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.context.AcdContext;

public class GenericAcdProcessor extends AcdProcessor {

    public GenericAcdProcessor() {
    }

    public GenericAcdProcessor(AcdContext acdContext) {
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
}
