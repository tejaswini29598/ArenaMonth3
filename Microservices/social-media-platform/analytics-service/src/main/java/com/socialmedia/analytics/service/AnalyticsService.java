package com.socialmedia.analytics.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class AnalyticsService {

    private AtomicLong totalEvents = new AtomicLong(0);
    private AtomicLong totalPosts = new AtomicLong(0);
    private AtomicLong totalUsers = new AtomicLong(0);

    @RabbitListener(queues = "post.created.queue")
    public void recordPostEvent(String message) {
        totalPosts.incrementAndGet();
        totalEvents.incrementAndGet();
        log.info("Analytics recorded - Total Posts: {}, Total Events: {}", totalPosts.get(), totalEvents.get());
    }

    public long getTotalEvents() {
        return totalEvents.get();
    }

    public long getTotalPosts() {
        return totalPosts.get();
    }

    public long getTotalUsers() {
        return totalUsers.get();
    }
}
