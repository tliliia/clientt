package sample;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Contract {
    public static final int RELEVANCE_DURATION_DAYS = 60;

    private int id;
    private Date created;
    private Date updated;
    private boolean isActual;

    public Contract() {}

    public Contract(int id, Date created, Date updated) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.isActual = updateActual();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
        this.isActual = updateActual();
    }

    public boolean getIsActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }

    private long daysBetweenTwoDates(Date date1, Date date2) {
        long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
        long difference = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return difference;
    }

    private boolean updateActual() {
        return daysBetweenTwoDates(updated, new Date()) < RELEVANCE_DURATION_DAYS;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", isActual=" + isActual +
                '}';
    }
}

