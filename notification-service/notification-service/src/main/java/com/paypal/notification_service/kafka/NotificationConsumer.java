package com.paypal.notification_service.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.paypal.notification_service.entity.Notification;
import com.paypal.notification_service.entity.Transaction;
import com.paypal.notification_service.repository.NotificationRepository;

@Component
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    public NotificationConsumer(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = "txn-initiated", groupId = "notification-group")
    public void consumeTransaction(Transaction transaction) {

        System.out.println("📥 Received transaction: " + transaction);

        // Notify sender
        Notification senderNotification = new Notification();
        senderNotification.setUserId(transaction.getSenderId());
        senderNotification.setMessage(
                "💸 You sent ₹" + transaction.getAmount() +
                " to user " + transaction.getReceiverId()
        );
        senderNotification.setSentAt(LocalDateTime.now());

        // Notify receiver
        Notification receiverNotification = new Notification();
        receiverNotification.setUserId(transaction.getReceiverId());
        receiverNotification.setMessage(
                "💰 You received ₹" + transaction.getAmount() +
                " from user " + transaction.getSenderId()
        );
        receiverNotification.setSentAt(LocalDateTime.now());

        notificationRepository.save(senderNotification);
        notificationRepository.save(receiverNotification);

        System.out.println("✅ Notifications saved");
    }
}