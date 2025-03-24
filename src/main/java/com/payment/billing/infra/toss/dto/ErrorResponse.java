package com.payment.billing.infra.toss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorResponse(
        String version,
        String traceId,
        Error error) {
    public record Error(
            String code,
            String message) {
    }
}
