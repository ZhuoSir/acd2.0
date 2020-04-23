package com.yuntongxun.acd.context;

import com.yuntongxun.acd.call.CallLineServantProcess;
import com.yuntongxun.acd.distribute.ServantDistributor;
import com.yuntongxun.acd.queue.LineElementQueue;

public interface AcdContext {

    boolean isNotify();

    boolean isCallable();

    boolean isSycn();

    LineElementQueue getLineElementQueue();

    ServantDistributor getServantDistributor();

    CallLineServantProcess getCallLineServantProcess();
}
