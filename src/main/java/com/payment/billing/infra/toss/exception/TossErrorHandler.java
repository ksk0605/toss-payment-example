package com.payment.billing.infra.toss.exception;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.billing.infra.toss.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TossErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError()
                || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error("토스 결제 API 오류 발생");
        log.error("응답 상태 코드: {}", response.getStatusCode());
        log.error("응답 헤더: {}", response.getHeaders());
        log.error("응답 본문: {}", response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse errorResponse = mapper.readValue(response.getBody(), ErrorResponse.class);

        log.error("결제 실패 - version: {}, traceId: {}, code: {}, message: {}",
                errorResponse.version(), errorResponse.traceId(),
                errorResponse.error() != null ? errorResponse.error().code() : "N/A",
                errorResponse.error() != null ? errorResponse.error().message() : "N/A");

        if (response.getStatusCode().is4xxClientError()) {
            throw new TossBillingException(
                    errorResponse.error() != null ? TossBillingErrorCode.getErrorCode(errorResponse.error().code())
                            : TossBillingErrorCode.INVALID_REQUEST);
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new TossBillingException(
                    errorResponse.error() != null ? TossBillingErrorCode.getErrorCode(errorResponse.error().code())
                            : TossBillingErrorCode.FAILED_INTERNAL_SYSTEM_PROCESSING);
        }
    }
}
