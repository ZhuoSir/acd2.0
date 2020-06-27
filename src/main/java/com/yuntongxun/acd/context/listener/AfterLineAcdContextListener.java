package com.yuntongxun.acd.context.listener;

import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;

public interface AfterLineAcdContextListener extends AcdContextListener {

    void afterLine(LineElement lineElement, LineServant lineServant);
}
