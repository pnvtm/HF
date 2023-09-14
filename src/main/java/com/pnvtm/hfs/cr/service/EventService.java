package com.pnvtm.hfs.cr.service;

public interface EventService {
    void placeEvent(long timestamp, double dataX, int dataY);

    String getStat();
}
