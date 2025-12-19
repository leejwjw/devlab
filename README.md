# DevLab - Spring Boot Tech Stack Demo

Spring Boot (Java 11) 기반으로 ElasticSearch, Redis, Kafka, Prometheus 등 다양한 기술 스택을 통합하고 테스트하는 데모 프로젝트입니다.

## 🛠 Tech Stack

- **Java**: 11
- **Framework**: Spring Boot 2.7.18
- **Database / Search**:
  - **ElasticSearch 7.17**: 검색 엔진 및 문서 저장소
  - **Redis**: 캐싱 및 세션 스토리지
- **Messaging**:
  - **Kafka**: 이벤트 스트리밍 및 메시지 큐
- **Monitoring**:
  - **Prometheus**: 메트릭 수집
  - **Grafana**: 메트릭 시각화
  - **Spring Boot Actuator**: 애플리케이션 상태 모니터링

## 🚀 Getting Started

### 1. 인프라 실행 (Docker Compose)

프로젝트 루트에서 다음 명령어를 실행하여 필요한 모든 서비스를 시작합니다. Spring Boot Docker Compose 지원으로 앱 실행시 자동 시작될 수도 있습니다.

```bash
docker-compose up -d
```

실행되는 서비스:
- **ElasticSearch**: http://localhost:9200
- **Redis**: localhost:6379
- **Kafka**: localhost:9092 (Zookeeper: 2181)
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (ID: admin / PW: secret)

### 2. 애플리케이션 실행

```bash
./gradlew bootRun
```

애플리케이션이 시작되면 http://localhost:8080 에서 접근 가능합니다.

## 🧪 API Usage Examples

### 1. ElasticSearch (Product Service)

- **제품 생성**:
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Laptop",
    "description": "High performance gaming laptop",
    "price": 1500.00,
    "category": "Electronics",
    "stock": 10,
    "brand": "TechMaster"
  }'
```

- **제품 검색 (이름)**:
```bash
curl "http://localhost:8080/api/products/search/name?name=Gaming"
```

### 2. Redis (Cache Service)

- **데이터 캐싱**:
```bash
curl -X PUT http://localhost:8080/api/cache \
  -H "Content-Type: application/json" \
  -d '{"key": "user:1", "value": "John Doe"}'
```

- **Redis 직접 저장 (TTL 설정)**:
```bash
curl -X POST "http://localhost:8080/api/cache/redis?key=session:123&value=active&timeout=3600"
```

### 3. Kafka (Event Service)

- **이벤트 발행**:
```bash
curl -X POST "http://localhost:8080/api/events?eventType=USER_CREATED&payload=user_123"
```
콘솔 로그에서 Consumer가 메시지를 수신하는 것을 확인할 수 있습니다.

### 4. Monitoring (Prometheus & Grafana)

- **메트릭 생성 테스트**:
```bash
curl -X POST "http://localhost:8080/api/metrics/operation?operation=HeavyTask"
```

- **Prometheus 확인**: http://localhost:9090 에서 `api_calls_total`, `api_response_time_seconds` 등의 메트릭 조회
- **Actuator 확인**: http://localhost:8080/actuator/prometheus

## 📁 Project Structure

```
src/main/java/com/example/devlab
├── config       # 설정 파일 (ES, Redis, Kafka, Metrics)
├── controller   # REST API 컨트롤러
├── domain       # 도메인/엔티티 모델
├── dto          # 데이터 전송 객체
├── repository   # 데이터 접근 계층
└── service      # 비즈니스 로직
```
