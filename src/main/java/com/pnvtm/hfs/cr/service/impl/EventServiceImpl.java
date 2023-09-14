package com.pnvtm.hfs.cr.service.impl;

import com.pnvtm.hfs.cr.domain.EventData;
import com.pnvtm.hfs.cr.domain.DataStatistics;
import com.pnvtm.hfs.cr.service.EventService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EventServiceImpl implements EventService {

    private static final Duration SLIDING_WINDOW_DURATION = Duration.ofSeconds(60);
    private final AtomicReference<DataStatistics> dataStatistics = new AtomicReference<>(new DataStatistics(SLIDING_WINDOW_DURATION));
    private final PriorityBlockingQueue<EventData> dataQueue = new PriorityBlockingQueue<>();

    public void placeEvent(EventData event) {

        Instant eventTime = Instant.ofEpochMilli(event.getTimestamp());
        Instant currentTime = Instant.now();
        if (eventTime.isBefore(currentTime.minus(SLIDING_WINDOW_DURATION))) {
            // Ignore data points outside the sliding window
            return;
        }

        // Add the event data to the blocking queue
        dataQueue.add(event);
    }

    public String getStat() {
        DataStatistics stats = dataStatistics.get();
        stats.update(dataQueue); // Update statistics based on the concurrent deque

        if (stats.isEmpty()) {
            throw new NotFoundException("No events inside the given interval.");
        } else {
            return stats.getStatistics();
        }
    }
}
