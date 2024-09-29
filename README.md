# musinsaAssignment

## 1. 개발 환경
- Spring Boot 3.3.4
- Java 21
- Database: H2
- ORM: JPA

## 2. 빌드 및 실행 방법
- 프로젝트 루트 디렉토리에서 아래 명령어 실행
```
git clone https://github.com/meengi07/musinsaAssignment.git

./gradlew clean build

java -jar build/libs/musinsaAssignment-0.0.1-SNAPSHOT.jar
```

## 3. API 명세
- [API 명세](http://localhost:8080/api-docs)

| 기능        | Method | API                              | Request                                                                     | Response                             |
|-----------|--------|----------------------------------|-----------------------------------------------------------------------------|--------------------------------------|
| 포인트 적립    | POST   | /api/point/earn                  | {"userId": 1, "amount": 100, "manually": false, "expireDate": "2024-09-29"} | {"pointId": 1, "remainPoint": 100}   |
| 포인트 적립 취소 | POST | /api/point/earn/{pointId}/cancel |                                                                             | {"pointId": 1, "remainPoint": 100}   |
| 포인트 사용    | POST   | /api/point/use                   | {"userId": 1, "orderId": 1, "amount": 100}                                  | {"remainPoint": 0}                   |
| 포인트 사용 취소 | POST | /api/point/use/cancel            | {"userId": 1, "orderId": 1, "amount": 100}                                  | {"remainPoint": 0}                   |


