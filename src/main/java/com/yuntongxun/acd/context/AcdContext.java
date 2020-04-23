package com.yuntongxun.acd.context;

import com.yuntongxun.acd.call.CallLineServantProcess;
import com.yuntongxun.acd.distribute.ServantDistributor;
import com.yuntongxun.acd.group.AcdGroup;
import com.yuntongxun.acd.proxy.ServiceProxy;
import com.yuntongxun.acd.queue.LineElementQueue;

public interface AcdContext {

    boolean isNotify();

    boolean isCallable();

    boolean isSycn();

    LineElementQueue getLineElementQueue();

    ServantDistributor getServantDistributor();

    CallLineServantProcess getCallLineServantProcess();

    ServiceProxy getServiceProxy();

    AcdGroup whichAcdGroup();
}
