package com.payment.billing.presentation;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.billing.application.BillingFacade;
import com.payment.billing.application.dto.CreateBillingRequest;
import com.payment.billing.application.dto.CreateBillingResponse;
import com.payment.common.APIResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/billings")
@RequiredArgsConstructor
public class BillingController {

    private final BillingFacade billingFacade;

    @PostMapping
    public APIResponse<CreateBillingResponse> createBilling(
            @Valid @RequestBody CreateBillingRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CreateBillingResponse response = billingFacade.createBilling(request, authentication.getName());
        return new APIResponse<>(response);
    }
}
