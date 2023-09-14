package com.pnvtm.hfs.cr.domain;

import lombok.Data;

@Data
public class EventData implements Comparable<EventData> {

    public EventData(long timestamp, double dataX, int dataY) {
        this.timestamp = timestamp;
        this.dataX = dataX;
        this.dataY = dataY;
    }

    private long timestamp;
    private double dataX;
    private int dataY;

    @Override
    public int compareTo(EventData other) {
        // Compare events based on timestamp
        return Long.compare(this.timestamp, other.timestamp);
    }

}
