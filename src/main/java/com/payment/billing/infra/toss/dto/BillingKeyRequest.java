package com.payment.billing.infra.toss.dto;

public record BillingKeyRequest(String authKey, String customerKey) {
}
