package com.socialmedia.notification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationListener {

    @RabbitListener(queues = "post.created.queue")
    public void handlePostCreatedEvent(String message) {
        log.info("Received post created event: {}", message);
        // Send notifications to followers
    }

    @RabbitListener(queues = "user.followed.queue")
    public void handleUserFollowedEvent(String message) {
        log.info("Received user followed event: {}", message);
        // Send follow notification
    }
}
