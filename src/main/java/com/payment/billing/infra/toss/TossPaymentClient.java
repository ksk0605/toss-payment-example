package com.payment.billing.infra.toss;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.payment.billing.domain.PaymentClient;
import com.payment.billing.infra.toss.dto.PaymentRequest;
import com.payment.billing.infra.toss.dto.PaymentResponse;
import com.payment.billing.infra.toss.exception.TossBillingException;
import com.payment.billing.infra.toss.exception.TossErrorHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentClient implements PaymentClient {

    private final RestClient tossRestClient;

    @Override
    public PaymentResponse pay(String billingKey, int amount, String customerKey, String orderId, String orderName,
                               String customerEmail) {
        log.info("결제 요청 - billingKey: {}, amount: {}, customerKey: {}, orderId: {}, orderName: {}", billingKey,
                amount, customerKey, orderId, orderName);

        try {
            PaymentResponse response = tossRestClient.post()
                    .uri("/billing/{billingKey}", billingKey)
                    .body(new PaymentRequest(amount, customerKey, orderId, orderName, customerEmail))
                    .retrieve()
                    .onStatus(new TossErrorHandler())
                    .body(PaymentResponse.class);
            log.info("결제 성공 - orderId: {}, paymentKey: {}", orderId, response.paymentKey());
            return response;
        } catch (RestClientException e) {
            log.error("결제 요청 타임아웃 - billingKey: {}, customerKey: {}, orderId: {}", billingKey, customerKey, orderId, e);
            throw new TossBillingException(HttpStatus.INTERNAL_SERVER_ERROR, "결제 요청 중 네트워크 오류가 발생했습니다.");
        }
    }
}
