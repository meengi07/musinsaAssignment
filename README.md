# musinsaAssignment

## 소개 
- 무신사 무료 포인트 시스템 (API) 과제를 수행한 프로젝트입니다.

## 1. 개발 환경
- Java 21
- Spring Boot 3.3.4
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
- API 명세 : http://localhost:8080/api-docs

| 기능        | Method | API                              | 
|-----------|--------|----------------------------------|
| 포인트 적립    | POST   | /api/point/earn                  | 
| 포인트 적립 취소 | POST | /api/point/earn/{pointId}/cancel |
| 포인트 사용    | POST   | /api/point/use                   |
| 포인트 사용 취소 | POST | /api/point/use/cancel            | 

### 1. 포인트 적립
- Request
```json
{
  "userId": 1,
    "amount": 100,
    "manually": false,
    "expireDate": "2024-09-29"
}
```
- Response
```json
{
  "pointId": 1,
  "remainPoint": 100
}
```

### 2. 포인트 적립 취소
- Request
```json
{
  "userId": 1,
  "pointId": 1
}
```
- Response
```json
{
  "remainPoint": 0
}
```

### 3. 포인트 사용
- Request
```json
{
  "userId": 1,
  "orderId": 1,
  "amount": 100
}
```
- Response
```json
{
  "remainPoint": 0
}
```

### 4. 포인트 사용 취소
- Request
```json
{
  "userId": 1,
  "orderId": 1,
  "amount": 100
}
```
- Response
```json
{
  "remainPoint": 0
}
```

## 4. 데이터베이스 스키마
- ERD 다이어그램
![image](/point-diagram-db.png)

## 5. AWS 환경을 가정한 아키텍처 구성
- AWS 환경을 가정한 아키텍처 구성도
  ![image](/point-diagram-aws.png)
