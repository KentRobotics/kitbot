package org.usfirst.frc.team2785.misc;

import java.util.Date;
import java.util.Iterator;

public class TableReader {
    private Date startTime;
    private Iterator<Double> datas;
    private Iterator<Long> times;
    private double currentValue = -1; //guarantees values will be refreshed on the first getReading
    private long currentTime = -1;
    public TableReader(Iterator<Double> dataIt, Iterator<Long> timeIt) {
        datas = dataIt;
        times = timeIt;
        reset();
    }
    public void reset() {
        startTime = new Date();
    }
    public boolean hasNext() {
        return datas.hasNext();
    }
    /**
     * Returns the value matching the current time offset from the last reset.
     * @return the current value for the current time
     */
    public double getReading() {
        Date now = new Date();
        while ((now.getTime() - startTime.getTime()) > currentTime && times.hasNext()) {
            currentTime = times.next();
            currentValue = datas.next();
        }
        return currentValue;
    }
}
