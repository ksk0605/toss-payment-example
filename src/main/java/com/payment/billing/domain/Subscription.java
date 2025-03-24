package com.payment.billing.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.payment.user.domain.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "subscription", indexes = {
        @Index(name = "idx_subscription_user_id", columnList = "user_id"),
        @Index(name = "idx_subscription_plan_id", columnList = "plan_id")
})
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_id_seq")
    @SequenceGenerator(name = "subscription_id_seq", sequenceName = "subscription_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Subscription(User user, Plan plan, LocalDate startDate) {
        this.user = user;
        this.plan = plan;
        this.status = SubscriptionStatus.ACTIVE;
        this.startDate = startDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel(LocalDate endDate) {
        this.status = SubscriptionStatus.CANCELED;
        this.endDate = endDate;
        this.updatedAt = LocalDateTime.now();
    }

    public void pause() {
        this.status = SubscriptionStatus.PAUSED;
        this.updatedAt = LocalDateTime.now();
    }

    public void resume() {
        this.status = SubscriptionStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void changePlan(Plan newPlan) {
        this.plan = newPlan;
        this.updatedAt = LocalDateTime.now();
    }
}
