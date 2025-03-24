# 토스페이먼츠 결제 연동 예시 프로젝트

이 프로젝트는 토스페이먼츠의 결제 API를 연동한 예시 프로젝트입니다. 
실제 프로덕션 환경에서 사용할 수 있는 수준의 결제 연동 구현을 목표로 합니다.

## 주요 구현 사항

### 1. RestClient를 활용한 HTTP 통신
- Spring의 RestClient를 사용하여 토스페이먼츠 API와의 통신 구현

### 2. 견고한 예외 처리
- 도메인별 커스텀 예외 클래스 구현
- 글로벌 예외 핸들러를 통한 일관된 에러 응답
- 비즈니스 로직별 명확한 예외 처리
- 토스 API 문서에 기반한 인프라 레이어 예외 처리 

### 3. 신뢰성 있는 테스트 구현
- WireMock을 활용한 외부 API 모킹
- 다양한 시나리오에 대한 통합 테스트 구현
- 테스트 데이터 지원을 위한 TestDataSupport 클래스 구현

### 4. TDD 기반 도메인 설계
- 테스트 주도 개발을 통한 견고한 도메인 모델 설계
- 도메인 로직의 캡슐화와 응집도 향상
- 명확한 도메인 규칙과 제약사항 정의

### 5. 계층형 아키텍처
- Application Layer: 유스케이스 구현
- Domain Layer: 핵심 비즈니스 로직
- Infrastructure Layer: 외부 시스템 연동
- Presentation Layer: API 엔드포인트

## 기술 스택
- Java 21
- Spring Boot 3.x
- Spring Cloud Contract WireMock
- JUnit 5
- AssertJ

## 프로젝트 구조
```
src/
├── main/
│   ├── java/
│   │   └── com/payment/
│   │       ├── billing/
│   │       │   ├── application/
│   │       │   ├── domain/
│   │       │   └── infra/
│   │       └── common/
│   └── resources/
└── test/
    └── java/
        └── com/payment/
            └── billing/
                └── application/
```

## 주요 기능
- 빌링키 발급 및 관리
- 자동 결제 처리
- 구독 상태 관리
- 결제 이력 조회

## API 문서
- 토스페이먼츠 API 문서: https://docs.tosspayments.com/
