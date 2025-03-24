package com.payment.billing.application;

import org.springframework.stereotype.Service;

import com.payment.billing.application.dto.CreateBillingRequest;
import com.payment.billing.application.dto.CreateBillingResponse;
import com.payment.billing.domain.Billing;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingFacade {
    private final BillingService billingService;
    private final SubscriptionService subscriptionService;
    private final PaymentService paymentService;

    @Transactional
    public CreateBillingResponse createBilling(CreateBillingRequest request, String email) {
        subscriptionService.create(request.planId(), email);
        Billing billing = billingService.issue(request.authKey(), request.customerKey(), email);
        paymentService.pay(billing, request.planId(), email);
        return new CreateBillingResponse(true);
    }
}
