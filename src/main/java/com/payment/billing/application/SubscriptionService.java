package com.payment.billing.application;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.payment.billing.application.exception.AlreadySubscribedException;
import com.payment.billing.application.exception.NoPlanException;
import com.payment.billing.domain.Plan;
import com.payment.billing.domain.PlanRepository;
import com.payment.billing.domain.Subscription;
import com.payment.billing.domain.SubscriptionRepository;
import com.payment.user.application.exception.NoUserException;
import com.payment.user.domain.User;
import com.payment.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public void create(Long planId, String email) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new NoPlanException());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoUserException());

        validateSubscriptionAlreadyExists(user, plan);

        Subscription subscription = new Subscription(user, plan, LocalDate.now());
        subscriptionRepository.save(subscription);
    }

    private void validateSubscriptionAlreadyExists(User user, Plan plan) {
        if (subscriptionRepository.existsByUserAndPlan(user, plan)) {
            throw new AlreadySubscribedException();
        }
    }
}
