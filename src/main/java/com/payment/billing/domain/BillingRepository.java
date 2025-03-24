package com.payment.billing.domain;

import org.springframework.data.repository.Repository;

public interface BillingRepository extends Repository<Billing, Long> {
    Billing save(Billing billing);

    void deleteAll();
}
