# Post Service

게시글과 카테고리 관리를 위한 마이크로서비스입니다.

## 🚀 주요 기능

- **게시글 관리**: 게시글 생성, 수정, 조회, 존재 여부 확인
- **카테고리 관리**: 메인/서브 카테고리 및 카테고리 리스트 관리
- **이벤트 스트리밍**: Kafka를 통한 게시글 생성 이벤트 발행

## 🏗️ 아키텍처

### 기술 스택
- **Framework**: Spring Boot 3.4.5
- **Language**: Java 17
- **Database**: 
  - MongoDB (Post 도메인)
  - MySQL (Category 도메인)
- **Message Queue**: Apache Kafka
- **Service Discovery**: Netflix Eureka
- **Documentation**: OpenAPI 3.0 (Swagger)

### MSA 구성 요소
- **API Gateway**: 인증 및 라우팅 처리
- **Service Discovery**: Eureka Client
- **Event Streaming**: Kafka Producer
- **Container**: Docker

## 📚 API 문서

### Swagger UI
- **개발 환경**: http://localhost:8080/swagger-ui.html
- **운영 환경**: http://your-domain/post-service/swagger-ui.html

### 주요 API 그룹

#### 1. Post API (`/api/v1/post`)
- `POST /create` - 게시글 생성
- `PATCH /{postUuid}` - 게시글 수정
- `GET /{postUuid}` - 게시글 정보 조회
- `GET /exist/{postUuid}` - 게시글 존재 여부 확인

#### 2. MainCategory API (`/api/v1/category/main`)
- `POST /main` - 메인 카테고리 생성
- `GET /main` - 메인 카테고리 전체 조회
- `GET /main/{id}` - 메인 카테고리 단건 조회
- `PUT /main/{id}` - 메인 카테고리 수정

#### 3. SubCategory API (`/api/v1/category/sub`)
- `POST /sub` - 서브 카테고리 생성
- `GET /sub` - 서브 카테고리 전체 조회
- `GET /sub/{id}` - 서브 카테고리 단건 조회
- `PUT /sub/{id}` - 서브 카테고리 수정
- `DELETE /sub/{id}` - 서브 카테고리 삭제

#### 4. CategoryList API (`/api/v1/category-list`)
- `POST /` - 카테고리 리스트 생성
- `GET /` - 카테고리 리스트 전체 조회
- `GET /{id}` - 카테고리 리스트 단건 조회
- `DELETE /{id}` - 카테고리 리스트 삭제
- `GET /main/{id}` - 메인 카테고리별 하위 카테고리 조회

## 🔐 인증

### Gateway 기반 인증
- JWT 토큰을 통한 인증
- `X-Member-UUID` 헤더를 통한 사용자 식별
- 각 API는 Gateway에서 인증된 요청만 처리

### 보안 헤더
```
X-Member-UUID: 550e8400-e29b-41d4-a716-446655440000
Authorization: Bearer <jwt-token>
```

## 🗄️ 데이터베이스

### MongoDB (Post)
```json
{
  "id": "507f1f77bcf86cd799439011",
  "postUuid": "550e8400-e29b-41d4-a716-446655440001",
  "memberUuid": "550e8400-e29b-41d4-a716-446655440000",
  "mainCategoryId": 1,
  "subCategoryId": 10,
  "title": "Spring Boot 마이크로서비스 아키텍처에 대한 질문",
  "contents": "MSA 환경에서 Spring Boot를 사용한 서비스 간 통신 방법에 대해 궁금합니다.",
  "blindStatus": false,
  "deletedStatus": false,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### MySQL (Category)
```sql
-- MainCategory
CREATE TABLE main_category (
    main_category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    main_category_name VARCHAR(100) NOT NULL,
    icon_url VARCHAR(500) NOT NULL,
    alt VARCHAR(200) NOT NULL,
    created_at DATETIME(0) NOT NULL,
    updated_at DATETIME(0) NOT NULL
);

-- SubCategory
CREATE TABLE sub_category (
    sub_category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sub_category_name VARCHAR(100) NOT NULL,
    color VARCHAR(7) NOT NULL,
    created_at DATETIME(0) NOT NULL,
    updated_at DATETIME(0) NOT NULL
);
```

## 🚀 배포

### Docker 실행
```bash
# 이미지 빌드
docker build -t post-service:latest .

# 컨테이너 실행
docker run -p 8080:8080 post-service:latest
```

### Docker Compose
```bash
docker-compose -f docker-compose-post.yml up -d
```

### 환경 변수
```bash
# 개발 환경
MYSQL_HOST=localhost
MYSQL_USER=root
MYSQL_PASSWORD=1234
MONGODB_URI_POST=mongodb://localhost:27017/learn_run_post

# 운영 환경
MYSQL_HOST=${MYSQL_HOST}
MYSQL_USER=${MYSQL_USER}
MYSQL_PASSWORD=${MYSQL_PASSWORD}
MONGODB_URI_POST=${MONGODB_URI_POST}
EC2_INFRA_HOST=${EC2_INFRA_HOST}
```

## 📊 모니터링

### 로깅
- **레벨**: INFO (운영), DEBUG (개발)
- **MongoDB 쿼리**: DEBUG 레벨로 로깅

### Kafka 설정
- **토픽**: `post-create-send-read`
- **Acks**: 1 (운영), 0 (개발)
- **Retries**: 3
- **Batch Size**: 16384

## 🧪 테스트

### 단위 테스트
```bash
./gradlew test
```

### API 테스트
Swagger UI를 통한 API 테스트 가능

## 📝 에러 코드

### Post 관련 (2000-2099)
- `2000`: 게시글을 찾을 수 없음
- `2001`: 게시글 작성 실패
- `2002`: 게시글 수정 실패
- `2003`: 게시글 삭제 실패
- `2004`: 게시글 내용이 너무 김
- `2005`: 게시글 제목이 너무 김
- `2007`: 게시글 형식이 잘못됨
- `2008`: 게시글 권한 없음

### Category 관련 (2100-2199)
- `2100`: 카테고리를 찾을 수 없음
- `2101`: 카테고리 생성 실패
- `2102`: 카테고리 수정 실패
- `2103`: 카테고리 삭제 실패
- `2104`: 중복된 카테고리명
- `2105`: 유효하지 않은 카테고리명
- `2106`: 중복된 카테고리

## 🤝 기여

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 📞 연락처

- **팀**: Post Service Team
- **이메일**: post-service@example.com
- **GitHub**: https://github.com/example/post-service