package bean;

import java.util.Date;

public class WorkTimeAgent extends Agent {

    private Date lastWorkTime;

    public void setLastWorkTime(Date lastWorkTime) {
        this.lastWorkTime = lastWorkTime;
    }

    public Date getLastWorkTime() {
        return lastWorkTime;
    }

    public WorkTimeAgent(String id) {
        super(id);
    }
}
