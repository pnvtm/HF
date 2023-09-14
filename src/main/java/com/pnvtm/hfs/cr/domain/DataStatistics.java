package com.pnvtm.hfs.cr.domain;

import org.webjars.NotFoundException;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.PriorityBlockingQueue;

public class DataStatistics {

    private final Duration slidingWindowDuration;
    private final Object lock = new Object();

    public DataStatistics(Duration slidingWindowDuration) {

        this.slidingWindowDuration = slidingWindowDuration;
    }

    public String getStatistics(PriorityBlockingQueue<EventData> dataQueue) {
        Instant currentTime = Instant.now();
        Instant windowStart = currentTime.minus(slidingWindowDuration);

        synchronized (lock) {
            // Remove events outside the sliding window
            while (true) {
                EventData event = dataQueue.peek();
                if (isOutsideSlidingWindow(event, windowStart)) {
                    // Event is outside the sliding window or queue is empty
                    dataQueue.poll();
                } else {
                    // All other Events is inside the sliding window
                    break;
                }
            }

            double sumX = dataQueue.stream().mapToDouble(EventData::getDataX).sum();
            double sumY = dataQueue.stream().mapToDouble(EventData::getDataY).sum();
            int count = dataQueue.size();

            if(count == 0){
                throw new NotFoundException("Storage is empty");
            }

            double avgX = sumX / count;
            double avgY = sumY / count;

            return String.format("%d,%.10f,%.10f,%.0f,%.3f", count, sumX, avgX, sumY, avgY);
        }
    }

    private static boolean isOutsideSlidingWindow(EventData event, Instant windowStart) {
        return event != null && Instant.ofEpochMilli(event.getTimestamp()).isBefore(windowStart);
    }
}
