package com.payment.billing.support;

import com.payment.billing.domain.Billing;
import com.payment.billing.domain.Payment;
import com.payment.billing.domain.Plan;
import com.payment.user.domain.User;

public class BillingFixture {

    public static User createUser() {
        return User.builder()
                .email("email")
                .name("name")
                .build();
    }

    public static Billing createBilling() {
        return new Billing(createUser(), "customerKey", "billingKey", "cardCompany", "cardNumber", "cardType",
                "ownerType");
    }

    public static Payment createPayment() {
        return new Payment("paymentKey", createBilling(), "orderId", 10000);
    }

    public static Plan createPlan() {
        return new Plan("name", 10000, 10, "description");
    }
}
