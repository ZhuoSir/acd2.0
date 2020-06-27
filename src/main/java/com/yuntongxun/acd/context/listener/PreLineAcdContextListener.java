package com.yuntongxun.acd.context.listener;

import com.yuntongxun.acd.common.LineElement;

public interface PreLineAcdContextListener extends AcdContextListener {
    void preLine(LineElement lineElement);
}
