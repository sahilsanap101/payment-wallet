package com.paypal.reward_service.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.paypal.reward_service.entity.Reward;
import com.paypal.reward_service.entity.Transaction;
import com.paypal.reward_service.repository.RewardRepository;

@Component
public class RewardConsumer {

    private final RewardRepository rewardRepository;

    public RewardConsumer(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @KafkaListener(topics = "txn-initiated", groupId = "reward-group")
    public void consumerTransaction(Transaction transaction){

        try {
            if (rewardRepository.existsByTransactionId(transaction.getId())) {
                System.out.println("⚠️ Reward already exists for txn: " + transaction.getId());
                return;
            }

            Reward reward = new Reward();

            // ✅ Reward RECEIVER (important)
            reward.setUserId(transaction.getReceiverId());

            // Example: 1% cashback
            reward.setPoints(transaction.getAmount() * 0.01);

            reward.setSentAt(LocalDateTime.now());
            reward.setTransactionId(transaction.getId());

            rewardRepository.save(reward);

            System.out.println("🎁 Reward given to user " + transaction.getReceiverId());

        } catch (Exception e) {
            System.err.println("❌ Reward failed: " + e.getMessage());
            throw e;
        }
    }
}