package com.yuntongxun.acd.common;

public abstract class LineServant {

    private String servantId;

    private boolean active = ACTIVE;

    public static boolean ACTIVE = true;
    public static boolean NOTACTIVE = false;

    public String getServantId() {
        return servantId;
    }

    public void setServantId(String servantId) {
        this.servantId = servantId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "LineServant{" +
                "servantId='" + servantId + '\'' +
                ", active=" + active +
                '}';
    }
}
