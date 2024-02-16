package com.pnvtm.hfs.cr.service;

import com.pnvtm.hfs.cr.domain.EventData;

public interface EventService {
    void placeEvent(EventData event);

    String getStat();
}
