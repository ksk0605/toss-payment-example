package com.payment.billing.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.payment.billing.application.exception.NoPlanException;
import com.payment.billing.domain.Billing;
import com.payment.billing.domain.Payment;
import com.payment.billing.domain.PaymentRepository;
import com.payment.billing.domain.Plan;
import com.payment.billing.domain.PlanRepository;
import com.payment.billing.domain.PaymentClient;
import com.payment.billing.infra.toss.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentClient paymentClient;
    private final PaymentRepository paymentRepository;
    private final PlanRepository planRepository;

    public void pay(Billing billing, Long planId, String customerEmail) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new NoPlanException());

        String orderId = generateOrderId();

        PaymentResponse response = paymentClient.pay(billing.getBillingKey(), plan.getPrice(),
                billing.getCustomerKey(), orderId, plan.getName(), customerEmail);

        Payment payment = new Payment(response.paymentKey(), billing, orderId, plan.getPrice());
        paymentRepository.save(payment);
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString();
    }
}
