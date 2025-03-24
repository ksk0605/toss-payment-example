package com.payment.billing.infra.toss;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.payment.billing.domain.BillingKeyClient;
import com.payment.billing.infra.toss.dto.BillingKeyRequest;
import com.payment.billing.infra.toss.dto.BillingKeyResponse;
import com.payment.billing.infra.toss.exception.TossBillingException;
import com.payment.billing.infra.toss.exception.TossErrorHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossBillingKeyClient implements BillingKeyClient {

    private final RestClient tossRestClient;

    @Override
    public BillingKeyResponse issueBillingKey(String authKey, String customerKey) {
        log.info("빌링키 발급 요청 - authKey: {}, customerKey: {}", authKey, customerKey);
        try {
            return tossRestClient.post()
                    .uri("/billing/authorizations/issue", authKey)
                    .body(new BillingKeyRequest(authKey, customerKey))
                    .retrieve()
                    .onStatus(new TossErrorHandler())
                    .body(BillingKeyResponse.class);
        } catch (RestClientException e) {
            log.error("빌링키 발급 요청 타임아웃 - authKey: {}, customerKey: {}", authKey, customerKey, e);
            throw new TossBillingException(HttpStatus.INTERNAL_SERVER_ERROR, "빌링키 발급 요청 중 네트워크 오류가 발생했습니다.");
        }
    }
}
