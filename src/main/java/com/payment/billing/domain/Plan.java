package com.payment.billing.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "plan")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_id_seq")
    @SequenceGenerator(name = "plan_id_seq", sequenceName = "plan_id_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "max_users", nullable = false)
    private Integer maxUsers;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Plan(String name, Integer price, Integer maxUsers, String description) {
        this.name = name;
        this.price = price;
        this.maxUsers = maxUsers;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String name, Integer price, Integer maxUsers, String description) {
        this.name = name;
        this.price = price;
        this.maxUsers = maxUsers;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}
