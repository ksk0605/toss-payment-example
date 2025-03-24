package com.payment.billing.infra.toss.exception;

import java.util.Arrays;

public enum TossBillingErrorCode {
    // 빌링키 요청
    INVALID_CARD_NUMBER("카드번호를 다시 확인해주세요.", "Please check your card number again."),
    NOT_SUPPORTED_CARD_TYPE("지원되지 않는 카드 종류입니다.", "This card type is not supported."),
    INVALID_CARD_PASSWORD("카드 정보를 다시 확인해주세요. (비밀번호)", "Please check the card information again."),
    INVALID_CARD_EXPIRATION("카드 정보를 다시 확인해주세요. (유효기간)", "Please check the card expiration date information again."),
    INVALID_CARD_IDENTITY("입력하신 주민번호/사업자번호가 카드 소유주 정보와 일치하지 않습니다.", "The entered resident registration number/business number does not match the cardholder information."),
    INVALID_REJECT_CARD("카드 사용이 거절되었습니다. 카드사 문의가 필요합니다.", "Refer to card issuer/decline."),
    INVALID_STOPPED_CARD("정지된 카드 입니다.", "This is a suspended card."),
    INVALID_BIRTH_DAY_FORMAT("생년월일 정보는 6자리의 `yyMMdd` 형식이어야 합니다. 사업자등록번호는 10자리의 숫자여야 합니다.", "Birth dates must be 6 digits long and in a `yyMMdd` format. Business number must be a 10 digit number."),
    NOT_REGISTERED_CARD_COMPANY("카드를 사용 등록 후 이용해주세요.", "Please register the credit card before using it."),
    INVALID_EMAIL("유효하지 않은 이메일 주소 형식입니다.", "Invalid email format"),
    NOT_SUPPORTED_METHOD("지원되지 않는 결제 수단입니다.", "This payment method is not supported."),
    INVALID_REQUEST("잘못된 요청입니다.", "The bad request."),
    EXCEED_MAX_AUTH_COUNT("최대 인증 횟수를 초과했습니다. 카드사로 문의해주세요.", "Maximum authentication attempts exceeded."),
    REJECT_CARD_COMPANY("결제 승인이 거절되었습니다.", "Payment confirm is rejected"),
    REJECT_ACCOUNT_PAYMENT("잔액부족으로 결제에 실패했습니다.", "Payment declined due to insufficient balance."),

    // 자동 결제 승인
    INVALID_BILL_KEY_REQUEST("빌링키 인증이 완료되지 않았거나 유효하지 않은 빌링 거래 건입니다.", "Unauthorized billingKey or invalid billing transaction"),
    DUPLICATED_ORDER_ID("이미 승인 및 취소가 진행된 중복된 주문번호 입니다. 다른 주문번호로 진행해주세요.", "This is a duplicate order id that has already been approved or canceled. Please proceed with a different order id."),
    NOT_SUPPORTED_INSTALLMENT_PLAN_CARD_OR_MERCHANT("할부가 지원되지 않는 카드 또는 가맹점 입니다.", "This card or merchant does not support installment."),
    INVALID_CARD_INSTALLMENT_PLAN("할부 개월 정보가 잘못되었습니다.", "The installment month information is incorrect."),
    NOT_MATCHES_CUSTOMER_KEY("빌링 인증 고객키와 결제 요청 고객키가 일치하지 않습니다.", "`customerKey` between billing auth request and payment request is not matching."),
    BELOW_MINIMUM_AMOUNT("신용카드는 결제금액이 100원 이상, 계좌는 200원이상부터 결제가 가능합니다.", "Payment can be made from 100 won or more by credit card, and 200 won or more for account."),
    NOT_SUPPORTED_MONTHLY_INSTALLMENT_PLAN("할부가 지원되지 않는 카드입니다.", "This card does not support installment."),
    UNAUTHORIZED_KEY("인증되지 않은 시크릿 키 혹은 클라이언트 키 입니다.", "Unauthorized secretKey or clientKey."),
    REJECT_CARD_PAYMENT("한도초과 혹은 잔액부족으로 결제에 실패했습니다.", "Payment failed due to limit exceeded or insufficient balance."),
    INCORRECT_BASIC_AUTH_FORMAT("잘못된 요청입니다. ':' 를 포함해 인코딩해주세요.", "Invalid request. Please encode including the ':' character."),
    FAILED_INTERNAL_SYSTEM_PROCESSING("내부 시스템 처리 작업이 실패했습니다. 잠시 후 다시 시도해주세요.", "Internal system processing operation has failed. Please try again in a few minutes."),
    FAILED_DB_PROCESSING("잘못된 요청 값으로 처리 중 DB 에러가 발생했습니다.", "A DB error occurred while processing with an invalid request value."),
    FAILED_CARD_COMPANY_RESPONSE("카드사에서 에러가 발생했습니다. 잠시 후 다시 시도해 주세요.", "An error occurred at the card company. Please try again in a few minutes.");

    private final String koreanMessage;
    private final String englishMessage;

    TossBillingErrorCode(String koreanMessage, String englishMessage) {
        this.koreanMessage = koreanMessage;
        this.englishMessage = englishMessage;
    }

    public static TossBillingErrorCode getErrorCode(String code) {
        return Arrays.stream(TossBillingErrorCode.values())
                .filter(errorCode -> errorCode.name().equals(code))
                .findFirst()
                .orElse(null);
    }

    public String getKoreanMessage() {
        return koreanMessage;
    }

    public String getEnglishMessage() {
        return englishMessage;
    }
}
