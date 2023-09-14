package com.pnvtm.hfs.cr.domain;

import lombok.Data;

@Data
public class EventData implements Comparable<EventData> {

    public EventData(Long timestamp, double dataX, int dataY) {
        this.timestamp = timestamp;
        this.dataX = dataX;
        this.dataY = dataY;
    }

    private Long timestamp;
    private double dataX;
    private int dataY;

    @Override
    public int compareTo(EventData other) {
        // Compare events based on timestamp
        return this.timestamp.compareTo(other.timestamp);
    }

}
