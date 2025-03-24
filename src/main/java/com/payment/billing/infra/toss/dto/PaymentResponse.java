package com.payment.billing.infra.toss.dto;

import java.util.List;

public record PaymentResponse(
        String version,
        String paymentKey,
        String type,
        String orderId,
        String orderName,
        String mId,
        String currency,
        String method,
        Long totalAmount,
        Long balanceAmount,
        String status,
        String requestedAt,
        String approvedAt,
        Boolean useEscrow,
        String lastTransactionKey,
        Long suppliedAmount,
        Long vat,
        Boolean cultureExpense,
        Long taxFreeAmount,
        Integer taxExemptionAmount,
        List<Cancel> cancels,
        Boolean isPartialCancelable) {
}

record Cancel(
        Long cancelAmount,
        String cancelReason,
        Long taxFreeAmount,
        Integer taxExemptionAmount,
        Long refundableAmount,
        Long transferDiscountAmount,
        Long easyPayDiscountAmount,
        String canceledAt,
        String transactionKey,
        String receiptKey,
        String cancelStatus,
        String cancelRequestId) {
}
