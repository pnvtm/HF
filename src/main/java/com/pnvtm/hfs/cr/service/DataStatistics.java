package com.pnvtm.hfs.cr.service;

import com.pnvtm.hfs.cr.domain.EventData;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.PriorityBlockingQueue;

public class DataStatistics {
    private double sumX;
    private double sumY;
    private long count;

    private final Duration slidingWindowDuration;
    private final Object lock = new Object();

    public DataStatistics(Duration slidingWindowDuration) {
        this.slidingWindowDuration = slidingWindowDuration;
    }

    public void update(PriorityBlockingQueue<EventData> dataQueue) {
        Instant currentTime = Instant.now();
        Instant windowStart = currentTime.minus(slidingWindowDuration);

        synchronized (lock) {
            // Remove events outside the sliding window
            while (true) {
                EventData event = dataQueue.peek();
                if (event != null && Instant.ofEpochMilli(event.getTimestamp()).isBefore(windowStart)) {
                    // Event is outside the sliding window or queue is empty
                    dataQueue.poll();
                } else {
                    // All other Events is inside the sliding window
                    break;
                }
            }

            sumX = dataQueue.stream().mapToDouble(EventData::getDataX).sum();
            sumY = dataQueue.stream().mapToDouble(EventData::getDataY).sum();
            count = dataQueue.size();
        }
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public String getStatistics() {
        synchronized (lock) {
            double avgX = count == 0 ? 0 : sumX / count;
            double avgY = count == 0 ? 0 : sumY / count;

            return String.format("%d,%.10f,%.10f,%.0f,%.3f", count, sumX, avgX, sumY, avgY);
        }
    }
}
