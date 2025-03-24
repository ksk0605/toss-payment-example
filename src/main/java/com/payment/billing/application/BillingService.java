package com.payment.billing.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.billing.domain.Billing;
import com.payment.billing.domain.BillingKeyClient;
import com.payment.billing.domain.BillingRepository;
import com.payment.billing.infra.toss.dto.BillingKeyResponse;
import com.payment.user.application.exception.NoUserException;
import com.payment.user.domain.User;
import com.payment.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingService {
        private final BillingRepository billingRepository;
        private final UserRepository userRepository;
        private final BillingKeyClient billingKeyClient;

        @Transactional
        public Billing issue(String authKey, String customerKey, String email) {
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new NoUserException());

                BillingKeyResponse billingKeyResponse = billingKeyClient.issueBillingKey(authKey, customerKey);

                Billing billing = new Billing(user, billingKeyResponse.customerKey(),
                                billingKeyResponse.billingKey(), billingKeyResponse.cardCompany(),
                                billingKeyResponse.cardNumber(), billingKeyResponse.card().cardType(),
                                billingKeyResponse.card().ownerType());
                billingRepository.save(billing);
                return billing;
        }
}
