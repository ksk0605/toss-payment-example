package com.payment.billing.domain;

import java.time.LocalDateTime;

import com.payment.user.domain.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "billing", indexes = {
        @Index(name = "idx_billing_user_id", columnList = "user_id"),
        @Index(name = "idx_billing_billing_key", columnList = "billing_key", unique = true)
})
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_id_seq")
    @SequenceGenerator(name = "billing_id_seq", sequenceName = "billing_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "customer_key", nullable = false)
    private String customerKey;

    @Column(name = "billing_key", nullable = false, unique = true)
    private String billingKey;

    @Column(name = "card_company")
    private String cardCompany;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "owner_type")
    private String ownerType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Billing(Long id, User user, String customerKey, String billingKey, String cardCompany,
            String cardNumber, String cardType, String ownerType, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.customerKey = customerKey;
        this.billingKey = billingKey;
        this.cardCompany = cardCompany;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.ownerType = ownerType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Billing(User user, String customerKey, String billingKey, String cardCompany,
            String cardNumber, String cardType, String ownerType) {
        this.user = user;
        this.customerKey = customerKey;
        this.billingKey = billingKey;
        this.cardCompany = cardCompany;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.ownerType = ownerType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCard(String billingKey, String cardCompany, String cardNumber,
            String cardType, String ownerType) {
        this.billingKey = billingKey;
        this.cardCompany = cardCompany;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.ownerType = ownerType;
        this.updatedAt = LocalDateTime.now();
    }
}
