package com.payment.billing.infra.toss.dto;

public record BillingKeyResponse(
        String mId, // 상점아이디
        String customerKey, // 구매자 ID
        String authenticatedAt, // 인증 시간
        String method, // 결제수단 (카드로 고정)
        String billingKey, // 자동결제용 빌링키
        String cardCompany, // 카드 발급사
        String cardNumber, // 마스킹된 카드 번호
        Card card // 카드 정보
) {
    public record Card(
            String issuerCode, // 카드 발급사 코드
            String acquirerCode, // 카드 매입사 코드
            String number, // 마스킹된 카드 번호
            String cardType, // 카드 종류 (신용, 체크, 기프트)
            String ownerType // 카드 소유자 타입 (개인, 법인)
    ) {
    }
}
