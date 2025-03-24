package com.payment.billing.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "payment", indexes = {
        @Index(name = "idx_payment_billing_id", columnList = "billing_id"),
        @Index(name = "idx_payment_payment_key", columnList = "payment_key", unique = true)
})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq")
    @SequenceGenerator(name = "payment_id_seq", sequenceName = "payment_id_seq")
    private Long id;

    @Column(name = "payment_key", nullable = false, unique = true)
    private String paymentKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id", nullable = false)
    private Billing billing;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "failure_code")
    private String failureCode;

    @Column(name = "failure_message")
    private String failureMessage;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Payment(String paymentKey, Billing billing, String orderId, Integer totalAmount) {
        this.paymentKey = paymentKey;
        this.billing = billing;
        this.orderId = orderId;
        this.status = PaymentStatus.PENDING;
        this.totalAmount = totalAmount;
        this.requestedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void approve(LocalDateTime approvedAt) {
        this.status = PaymentStatus.DONE;
        this.approvedAt = approvedAt;
        this.updatedAt = LocalDateTime.now();
    }

    public void fail(String failureCode, String failureMessage) {
        this.status = PaymentStatus.FAILED;
        this.failureCode = failureCode;
        this.failureMessage = failureMessage;
        this.updatedAt = LocalDateTime.now();
    }
}
