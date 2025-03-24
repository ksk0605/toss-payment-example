package com.payment.billing.domain;

import com.payment.billing.support.BillingFixture;
import com.payment.user.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriptionTest {
    @Test
    void cancel() {
        // given
        User user = BillingFixture.createUser();
        Plan plan = BillingFixture.createPlan();
        Subscription subscription = new Subscription(user, plan, LocalDate.now());

        // when
        subscription.cancel(LocalDate.now().plusDays(10));

        // then
        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.CANCELED);
        assertThat(subscription.getEndDate()).isEqualTo(LocalDate.now().plusDays(10));
    }

    @Test
    void changePlan() {
        // given
        User user = BillingFixture.createUser();
        Plan plan = BillingFixture.createPlan();
        Subscription subscription = new Subscription(user, plan, LocalDate.now());

        // when
        Plan newPlan = BillingFixture.createPlan();
        subscription.changePlan(newPlan);

        // then
        assertThat(subscription.getPlan()).isEqualTo(newPlan);
        assertThat(subscription.getUpdatedAt()).isNotNull();
    }

    @Test
    void pause() {
        // given
        User user = BillingFixture.createUser();
        Plan plan = BillingFixture.createPlan();
        Subscription subscription = new Subscription(user, plan, LocalDate.now());

        // when
        subscription.pause();

        // then
        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.PAUSED);
        assertThat(subscription.getUpdatedAt()).isNotNull();
    }

    @Test
    void resume() {
        // given
        User user = BillingFixture.createUser();
        Plan plan = BillingFixture.createPlan();
        Subscription subscription = new Subscription(user, plan, LocalDate.now());

        // when
        subscription.resume();

        // then
        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
        assertThat(subscription.getUpdatedAt()).isNotNull();
    }
}
