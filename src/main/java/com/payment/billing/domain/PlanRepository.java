package com.payment.billing.domain;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface PlanRepository extends Repository<Plan, Long> {
    Optional<Plan> findById(long id);

    Plan save(Plan plan);

    void deleteAll();
}
