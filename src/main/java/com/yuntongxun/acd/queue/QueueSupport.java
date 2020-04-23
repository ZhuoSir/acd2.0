package com.yuntongxun.acd.queue;


import com.yuntongxun.acd.common.LineElement;

public interface QueueSupport {

    void line(LineElement lineElement);

    void lineFailed(LineElement lineElement);

    void cancelLine(LineElement lineElement);

    void linePriority(LineElement lineElement);

}
