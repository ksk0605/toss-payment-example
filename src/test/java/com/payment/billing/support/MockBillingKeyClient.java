package com.payment.billing.support;

import com.srsr.api.billing.domain.BillingKeyClient;
import com.srsr.api.billing.infra.toss.dto.BillingKeyResponse;

public class MockBillingKeyClient implements BillingKeyClient {

    @Override
    public BillingKeyResponse issueBillingKey(String authKey, String customerKey) {
        return new BillingKeyResponse("mId", customerKey, "2024-01-01", "card", "billingKey",
                "cardCompany", "cardNumber",
                new BillingKeyResponse.Card("issuerCode", "acquirerCode", "number", "cardType", "ownerType"));
    }
}
