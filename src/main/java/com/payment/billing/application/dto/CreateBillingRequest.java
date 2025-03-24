package com.payment.billing.application.dto;

public record CreateBillingRequest(Long planId, String authKey, String customerKey) {

}
