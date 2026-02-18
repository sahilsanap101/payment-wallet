package com.paypal.reward_service.service;

import java.util.List;

import com.paypal.reward_service.entity.Reward;

public interface RewardService {
    Reward sendReward(Reward reward);
    List<Reward> getRewardsByUserId(Long UserId);
}