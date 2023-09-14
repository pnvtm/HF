package com.pnvtm.hfs.cr.service.impl;

import com.pnvtm.hfs.cr.domain.EventData;
import com.pnvtm.hfs.cr.service.DataStatistics;
import com.pnvtm.hfs.cr.service.EventService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EventServiceImpl implements EventService {

    private static final int seconds = 60;
    private static final Duration SLIDING_WINDOW_DURATION = Duration.ofSeconds(seconds);
    private final AtomicReference<DataStatistics> dataStatistics = new AtomicReference<>(new DataStatistics(SLIDING_WINDOW_DURATION));
    private final PriorityBlockingQueue<EventData> dataQueue = new PriorityBlockingQueue<>(seconds * 1000); // seconds in milliseconds

    public void placeEvent(long timestamp, double dataX, int dataY) {

        Instant eventTime = Instant.ofEpochMilli(timestamp);
        Instant currentTime = Instant.now();
        if (eventTime.isBefore(currentTime.minus(SLIDING_WINDOW_DURATION))) {
            // Ignore data points outside the sliding window
            return;
        }

        // Add the event data to the blocking queue
        dataQueue.add(new EventData(timestamp, dataX, dataY));
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
