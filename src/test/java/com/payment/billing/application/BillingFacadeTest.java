package com.payment.billing.application;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.payment.TestDataSupport;
import com.payment.billing.application.dto.CreateBillingRequest;
import com.payment.billing.application.dto.CreateBillingResponse;
import com.payment.billing.application.exception.AlreadySubscribedException;
import com.payment.billing.domain.Subscription;
import com.payment.billing.domain.SubscriptionRepository;
import com.payment.billing.infra.toss.exception.TossBillingException;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
public class BillingFacadeTest extends TestDataSupport {

        @Autowired
        private BillingFacade billingFacade;

        @Autowired
        private SubscriptionRepository subscriptionRepository;

        @Test
        void createBilling_successfulPayment() {
                stubFor(post(urlEqualTo("/billing/authorizations/issue"))
                                .willReturn(aResponse()
                                                .withStatus(HttpStatus.OK.value())
                                                .withHeader("Content-Type", "application/json")
                                                .withBodyFile("billing-key-response.json")));

                stubFor(post(urlEqualTo("/billing/billing_key_123"))
                                .willReturn(aResponse()
                                                .withStatus(HttpStatus.OK.value())
                                                .withHeader("Content-Type", "application/json")
                                                .withBodyFile("payment-response.json")));

                // 사용자 요청 시나리오
                CreateBillingRequest request = new CreateBillingRequest(plan.getId(), "authKey123", "customerKey123");
                CreateBillingResponse response = billingFacade.createBilling(request, user.getEmail());

                // 결과 검증
                assertThat(response.success()).isTrue();
                assertThat(subscriptionRepository.existsByUserAndPlan(user, plan)).isTrue();
        }

        @Test
        void createBilling_alreadySubscribed() {
                // 이미 구독한 사용자 등록
                stubFor(post(urlEqualTo("/billing/authorizations/issue"))
                                .willReturn(aResponse()
                                                .withStatus(HttpStatus.OK.value())
                                                .withHeader("Content-Type", "application/json")
                                                .withBodyFile("billing-key-response.json")));

                // 동일한 사용자가 이미 구독 중인 상태 시뮬레이션
                subscriptionRepository.save(new Subscription(user, plan, LocalDate.now()));

                // 사용자 요청 시나리오
                CreateBillingRequest request = new CreateBillingRequest(plan.getId(), "authKey123", "customerKey123");

                // 결과 검증: 이미 구독한 경우 예외 발생
                assertThatThrownBy(() -> billingFacade.createBilling(request, user.getEmail()))
                                .isInstanceOf(AlreadySubscribedException.class);
        }

        @Test
        void createBilling_billingKeyIssueFailed() {
                // 빌링키 발급 실패 Mock 설정
                stubFor(post(urlEqualTo("/billing/authorizations/issue"))
                                .willReturn(aResponse()
                                                .withStatus(HttpStatus.BAD_REQUEST.value())
                                                .withHeader("Content-Type", "application/json")
                                                .withBodyFile("billing-key-fail-response.json")));

                // 사용자 요청 시나리오
                CreateBillingRequest request = new CreateBillingRequest(plan.getId(), "authKey123",
                                "invalidCustomerKey");

                // 결과 검증: 빌링키 발급 실패
                assertThatThrownBy(() -> billingFacade.createBilling(request, user.getEmail()))
                                .isInstanceOf(TossBillingException.class)
                                .hasMessageContaining("잘못된 accessKey 입니다");
                assertThat(subscriptionRepository.existsByUserAndPlan(user, plan)).isFalse();
        }

        @Test
        void createBilling_paymentFailed() {
                // 빌링키 발급 성공 Mock 설정
                stubFor(post(urlEqualTo("/billing/authorizations/issue"))
                                .willReturn(aResponse()
                                                .withStatus(HttpStatus.OK.value())
                                                .withHeader("Content-Type", "application/json")
                                                .withBodyFile("billing-key-response.json")));

                // 결제 실패 Mock 설정
                stubFor(post(urlEqualTo("/billing/billing_key_123"))
                                .willReturn(aResponse()
                                                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                .withHeader("Content-Type", "application/json")
                                                .withBodyFile("payment-fail-response.json")));

                // 사용자 요청 시나리오
                CreateBillingRequest request = new CreateBillingRequest(plan.getId(), "authKey123", "customerKey123");

                // 결과 검증: 결제 실패
                assertThatThrownBy(() -> billingFacade.createBilling(request, user.getEmail()))
                                .isInstanceOf(TossBillingException.class)
                                .hasMessageContaining("잘못된 카드번호 입니다.");
                assertThat(subscriptionRepository.existsByUserAndPlan(user, plan)).isFalse();
        }
}
