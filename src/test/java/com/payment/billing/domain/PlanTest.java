package com.payment.billing.domain;

import com.srsr.api.billing.support.BillingFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanTest {
    @Test
    void update() {
        // given
        Plan plan = BillingFixture.createPlan();

        // when
        plan.update("name", 20000, 20, "description");

        // then
        assertThat(plan.getName()).isEqualTo("name");
        assertThat(plan.getPrice()).isEqualTo(20000);
        assertThat(plan.getMaxUsers()).isEqualTo(20);
        assertThat(plan.getDescription()).isEqualTo("description");
    }
}
