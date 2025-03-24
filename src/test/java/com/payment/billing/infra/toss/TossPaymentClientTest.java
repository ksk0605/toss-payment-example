package com.payment.billing.infra.toss;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.billing.infra.toss.dto.ErrorResponse;
import com.payment.billing.infra.toss.exception.TossBillingException;
import com.payment.billing.infra.toss.exception.TossErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class TossPaymentClientTest {

    private RestClient.Builder tossRestClientBuilder = RestClient.builder()
            .baseUrl("https://api.tosspayments.com/v1")
            .defaultStatusHandler(new TossErrorHandler());

    private MockRestServiceServer mockServer = MockRestServiceServer.bindTo(tossRestClientBuilder).build();

    private TossPaymentClient tossPaymentClient = new TossPaymentClient(tossRestClientBuilder.build());

    @BeforeEach
    void setUp() {
        mockServer.reset();
    }

    @Test
    @DisplayName("결제 요청 시 4xx 에러가 발생하면 TossBillingException이 발생해야 한다")
    void shouldThrowTossBillingExceptionWhen4xxError() throws Exception {
        // given
        ErrorResponse errorResponse = new ErrorResponse(
                "2022-11-16",
                "1234567890",
                new ErrorResponse.Error(
                        "INVALID_CARD_NUMBER",
                        "유효하지 않은 카드번호입니다"));

        mockServer.expect(requestTo("https://api.tosspayments.com/v1/billing/testBillingKey"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(errorResponse)));

        // when & then
        assertThrows(TossBillingException.class, () -> {
            tossPaymentClient.pay("testBillingKey", 1000, "testCustomerKey", "testOrderId", "testOrderName",
                    "testCustomerEmail");
        });

        mockServer.verify();
    }

    @Test
    @DisplayName("결제 요청 시 5xx 에러가 발생하면 TossBillingException이 발생해야 한다")
    void shouldThrowTossBillingExceptionWhen5xxError() throws Exception {
        // given
        ErrorResponse errorResponse = new ErrorResponse(
                "2022-11-16",
                "1234567890",
                new ErrorResponse.Error(
                        "INTERNAL_ERROR",
                        "서버 에러가 발생했습니다"));

        mockServer.expect(requestTo("https://api.tosspayments.com/v1/billing/testBillingKey"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(errorResponse)));

        // when & then
        assertThrows(TossBillingException.class, () -> {
            tossPaymentClient.pay("testBillingKey", 1000, "testCustomerKey", "testOrderId", "testOrderName",
                    "testCustomerEmail");
        });

        mockServer.verify();
    }
}
