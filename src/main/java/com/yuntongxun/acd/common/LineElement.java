package com.yuntongxun.acd.common;

public abstract class LineElement {

    public abstract String index();

    protected String groupId;

    public LineElement() {
    }

    public LineElement(String groupId) {
        this.groupId = groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    private int waitingCount;

    public void setWaitingCount(int waitingCount) {
        this.waitingCount = waitingCount;
    }

    public int getWaitingCount() {
        return waitingCount;
    }

    @Override
    public String toString() {
        return "LineElement{" +
                "groupId='" + groupId + '\'' +
                ", waitingCount=" + waitingCount +
                '}';
    }

    public LineElementInfo getInfo() {
        LineElementInfo info = new LineElementInfo();
        info.setIndex(index());
        info.setWaitingCount(waitingCount);
        info.setGroupId(groupId);
        return info;
    }
}
