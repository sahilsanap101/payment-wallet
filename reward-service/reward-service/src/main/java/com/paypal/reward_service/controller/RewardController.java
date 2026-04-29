package com.paypal.reward_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.reward_service.entity.Reward;
import com.paypal.reward_service.repository.RewardRepository;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardRepository rewardRepository;

    public RewardController(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @GetMapping("/me")
    public List<Reward> getMyRewards(
            @RequestHeader("X-User-Id") String userId
    ) {
        return rewardRepository.findByUserId(Long.parseLong(userId));
    }

    @GetMapping("/all")
    public List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }
}