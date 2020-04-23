package com.yuntongxun.acd.common;

public class LineElementInfo {

    private int waitingCount;

    private String groupId;

    private String index;

    public int getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(int waitingCount) {
        this.waitingCount = waitingCount;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "LineElementInfo{" +
                "waitingCount=" + waitingCount +
                ", groupId='" + groupId + '\'' +
                ", index='" + index + '\'' +
                '}';
    }
}
