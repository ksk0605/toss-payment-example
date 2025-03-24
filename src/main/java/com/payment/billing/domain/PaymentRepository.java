package com.payment.billing.domain;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface PaymentRepository extends Repository<Payment, Long> {

    Payment save(Payment payment);

    Optional<Payment> findByOrderId(String orderId);

    void deleteAll();
}
