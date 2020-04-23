package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.queue.bean.QueueNotification;

public interface QueueNotifyProxy {

    void sendQueueNotification(QueueNotification queueNotification);
}
