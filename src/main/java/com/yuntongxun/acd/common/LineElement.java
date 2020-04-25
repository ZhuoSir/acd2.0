package com.yuntongxun.acd.common;

public abstract class LineElement implements Comparable<LineElement> {

    public abstract String index();

    protected String groupId;

    public LineElement() {
    }

    public LineElement(int priority) {
        this.priority = priority;
    }

    /** *
     *
     *  排队重量级别：0, 1，2；
     *  级别高，排队优先；
     */
    public int priority;

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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return "LineElement{" +
                "groupId='" + groupId + '\'' +
                ", waitingCount=" + waitingCount +
                '}';
    }

    @Override
    public int compareTo(LineElement o) {
        if (this.priority < o.getPriority()) {
            return -1;
        } else if (this.priority > o.getPriority()) {
            return 1;
        } else {
            return -1;
        }
    }

    public LineElementInfo getInfo() {
        LineElementInfo info = new LineElementInfo();
        info.setIndex(index());
        info.setWaitingCount(waitingCount);
        info.setGroupId(groupId);
        return info;
    }
}
