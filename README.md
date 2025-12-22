# DevLab - Spring Boot Tech Stack Demo

Spring Boot (Java 11) 기반으로 ElasticSearch, Redis, Kafka, Prometheus 등 다양한 기술 스택을 통합하고 테스트하는 데모 프로젝트입니다.

## Tech Stack

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

## Getting Started

### 1. 인프라 실행

프로젝트 루트에서 다음 명령어를 실행하여 필요한 모든 서비스를 시작합니다.

```bash
docker-compose up -d
docker-compose ps  # 서비스 상태 확인
```

실행되는 서비스:
- **ElasticSearch**: http://localhost:9200
- **Kibana** (ElasticSearch UI): http://localhost:5601
- **Redis**: localhost:6379
- **RedisInsight** (Redis GUI): http://localhost:5540
- **Kafka**: localhost:9092 (Zookeeper: 2181)
- **Kafka UI**: http://localhost:8090
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (ID: admin / PW: secret)

### 2. 애플리케이션 실행

```bash
./gradlew bootRun
```

애플리케이션이 시작되면 http://localhost:8080 에서 접근 가능합니다.

## Prometheus & Grafana 테스트

### Step 1: Actuator 메트릭 확인

```bash
# Prometheus 메트릭 엔드포인트
curl http://localhost:8080/actuator/prometheus

# Health 체크
curl http://localhost:8080/actuator/health
```

### Step 2: Prometheus 타겟 확인

1. http://localhost:9090 접속
2. `Status` → `Targets` 메뉴 선택
3. `spring-actuator` job이 UP 상태인지 확인

### Step 3: 메트릭 생성

```bash
# 메트릭 생성 테스트
curl -X POST "http://localhost:8080/api/metrics/operation?operation=TestOperation"

# 이벤트 발행
curl -X POST "http://localhost:8080/api/events?eventType=USER_CREATED&payload=user_123"

# 반복 호출 (메트릭 데이터 축적)
for i in {1..10}; do
  curl -X POST "http://localhost:8080/api/metrics/operation?operation=HeavyTask"
done
```

### Step 4: Prometheus 쿼리

Prometheus UI의 Graph 탭에서 다음 쿼리 실행:

```promql
# JVM 힙 메모리 사용량
jvm_memory_used_bytes{area="heap"}

# HTTP 요청 수 (최근 5분간)
rate(http_server_requests_seconds_count[5m])

# 커스텀 메트릭
api_calls_total
api_response_time_seconds
```

### Step 5: Grafana 설정

**로그인**
- URL: http://localhost:3000
- Username: `admin`
- Password: `secret`

**Prometheus 데이터소스 추가**
1. `Configuration` → `Data Sources` 선택
2. `Add data source` 클릭
3. `Prometheus` 선택
4. URL: `http://prometheus:9090` 입력
5. `Save & Test` 클릭

**대시보드 생성**

추천 메트릭:
- `rate(http_server_requests_seconds_count{application="devlab"}[5m])` - HTTP 요청률
- `jvm_memory_used_bytes{application="devlab",area="heap"} / 1024 / 1024` - JVM 힙 메모리 (MB)
- `api_calls_total{application="devlab"}` - API 호출 횟수
- `rate(api_response_time_seconds_sum[5m]) / rate(api_response_time_seconds_count[5m])` - 평균 응답 시간

### Step 6: 실시간 모니터링

```bash
# 반복 호출하여 실시간 변화 확인 (Ctrl+C로 중지)
while true; do
  curl -X POST "http://localhost:8080/api/metrics/operation?operation=LoadTest"
  sleep 1
done
```

## API Usage Examples

### ElasticSearch (Product Service)

**제품 생성**
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

**제품 검색**
```bash
curl "http://localhost:8080/api/products/search/name?name=Gaming"
```

### Redis (Cache Service)

**데이터 캐싱**
```bash
curl -X PUT http://localhost:8080/api/cache \
  -H "Content-Type: application/json" \
  -d '{"key": "user:1", "value": "John Doe"}'
```

**Redis 직접 저장 (TTL 설정)**
```bash
curl -X POST "http://localhost:8080/api/cache/redis?key=session:123&value=active&timeout=3600"
```

### Kafka (Event Service)

**이벤트 발행**
```bash
curl -X POST "http://localhost:8080/api/events?eventType=USER_CREATED&payload=user_123"
```

## Project Structure

```
src/main/java/com/example/devlab
├── config       # 설정 파일 (ES, Redis, Kafka, Metrics)
├── controller   # REST API 컨트롤러
├── domain       # 도메인/엔티티 모델
├── dto          # 데이터 전송 객체
├── repository   # 데이터 접근 계층
└── service      # 비즈니스 로직
```

## Shutdown

```bash
# 애플리케이션 중지 (Ctrl+C)

# Docker 서비스 중지
docker-compose down

# 볼륨까지 삭제 (데이터 초기화)
docker-compose down -v
```
