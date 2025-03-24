package com.payment.billing.domain;

import org.springframework.data.repository.Repository;

import com.payment.user.domain.User;

public interface SubscriptionRepository extends Repository<Subscription, Long> {
    Subscription save(Subscription subscription);

    boolean existsByUserAndPlan(User user, Plan plan);

    void deleteAll();
}
