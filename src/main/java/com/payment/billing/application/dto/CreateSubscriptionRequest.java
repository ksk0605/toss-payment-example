package com.payment.billing.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CreateSubscriptionRequest {
    @NotNull(message = "플랜 아이디는 필수입니다.")
    private Long planId;
}
