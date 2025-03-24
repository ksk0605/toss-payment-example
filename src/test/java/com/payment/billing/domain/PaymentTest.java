package com.payment.billing.domain;

import com.payment.billing.support.BillingFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest {
    @Test
    void approve() {
        // given
        Payment payment = BillingFixture.createPayment();

        // when
        payment.approve(LocalDateTime.now());

        // then
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.DONE);
        assertThat(payment.getApprovedAt()).isNotNull();
        assertThat(payment.getUpdatedAt()).isNotNull();
    }

    @Test
    void fail() {
        // given
        Payment payment = BillingFixture.createPayment();

        // when
        payment.fail("failureCode", "failureMessage");

        // then
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.FAILED);
        assertThat(payment.getFailureCode()).isEqualTo("failureCode");
        assertThat(payment.getFailureMessage()).isEqualTo("failureMessage");
    }
}
