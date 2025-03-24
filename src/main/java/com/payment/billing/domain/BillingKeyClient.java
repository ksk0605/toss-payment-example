package com.payment.billing.domain;

import com.payment.billing.infra.toss.dto.BillingKeyResponse;

public interface BillingKeyClient {
    BillingKeyResponse issueBillingKey(String authKey, String customerKey);
}
