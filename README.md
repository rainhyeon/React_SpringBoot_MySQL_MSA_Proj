# 📈 프로젝트 개요

- 기존 모놀리스(Springboot-JPA-Blog) 구조를 MSA 아키텍쳐로 전환하여, 서비스별 독립 개발 및 배포 가능하도록 구성
- **React (Frontend)**, **Spring Boot (Backend)**, MySQL (DB)로 서비스 분리
- 개발 및 테스트 환경에서는 **Docker Compose**로 로컬 통합 시톱
- 실제 배포 환경에서는 **AWS ECS + ECR** 기반으로 운영 가능하도록 구조 개정
- nginx 프로시 설정을 환경변수 기반으로 템플릿화하여, 다양한 배포 환경(ECS, EC2 등)에서도 재사용 가능
- API 중심 구조로 프론트/백어드 완전 분리 및 확장성 확률

---

## 통신 구조 (Board → User)

- `board-service`는 계정 userId가 존재하는지를 `user-service`에 API 통신으로 확인
- RestTemplate 사용 + 환경값과 application.yml 연동

```yaml
# board-service/src/main/resources/application.yml
user-service:
  url: http://${USER_API_HOST:user-service}:${USER_API_PORT:8081}
```

```java
@Value("${user-service.url}")
private String userServiceUrl;

Boolean userExists = restTemplate.getForObject(
    userServiceUrl + "/api/users/" + userId + "/exists",
    Boolean.class
);
```

> 환경에 따라 자동으로 URL 가 변경되기 때문에 Docker 뿐만 아니라 ECS에서도 유용

---

## ⚙️ nginx 프로시 구성 (frontend)

React에서 API 요청은 `/api/users/*`, `/api/board/*` 경로로 보내며, nginx는 다음과 같이 backend에 프로시화함:

```
/api/users  →  user-service
/api/board  →  board-service
```

그리고 프로시 주소는 환경변수 기반으로 설정되어, Docker 환경 및 ECS 배포에서도 재사용 가능해지는 구조로 개정되었음.

> `nginx.conf`는 `/frontend/nginx.conf.template`에서 환경값 `${USER_API_HOST}`, `${BOARD_API_HOST}`를 템플릿 컨퍼에스트로 변경 적용해 생성됩니다.
> Docker 또는 ECS 같은 건설 환경에서 동일하게 nginx 구성해줍니다.

---

## Dockerfile (프론트엔드)

```dockerfile
FROM node:18-alpine AS builder
WORKDIR /app
COPY . .
ENV NODE_OPTIONS=--openssl-legacy-provider
RUN npm install
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf.template /etc/nginx/templates/nginx.conf.template
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
```

> entrypoint.sh는 환경값를 nginx.conf로 변환하고 시작합니다.

---

## docker-compose 환경변수 설정 예시

```yaml
frontend:
  environment:
    BOARD_API_HOST: board-service
    BOARD_API_PORT: 8080
    USER_API_HOST: user-service
    USER_API_PORT: 8081

board-service:
  environment:
    SPRING_DATASOURCE_URL: jdbc:mysql://board-db:3306/board_db
    USER_API_HOST: user-service
    USER_API_PORT: 8081
```

---
## Docker 설치 (Ubuntu)

```bash
curl -fsSL https://get.docker.com -o get-docker.sh
chmod +x get-docker.sh
./get-docker.sh
```

---

## 통합 서비스 실행

```bash
git clone https://github.com/rainhyeon/MSA_ECR_ECS_Prj.git
mv MSA_ECR_ECS_Prj WebSite
cd WebSite
docker compose up -d --build
```

---



## 서비스 단위 발더 & 시작

### frontend
```bash
docker compose build frontend
docker compose up frontend -d
```

### user-service
```bash
docker compose build user-service
docker compose up user-service -d
```

### board-service
```bash
docker compose build board-service
docker compose up board-service -d
```

---

## 로그 확인

```bash
docker ps
docker logs ecr_prj-frontend-1
```

---

## 웹 접속

```
http://localhost:3000
```

- 회원가입, 로그인, 게시물 기능
- API 호출은 nginx 프로시를 거치

---

## DB 확인

### user-db
```bash
docker exec -it user-db sh
mysql -u root -ppassword
> use users;
> select * from users;
```

### board-db
```bash
docker exec -it board-db sh
mysql -u root -ppassword
> use board_db;
> select * from board;
```

---

## 커테이너 구조

| 서비스 | 설명 |
|------------|--------|
| frontend | React 정적 파일 + nginx 프록시 서버 |
| user-service | 회원가입/로그인 API (Spring Boot) |
| board-service | 게시판 API (Spring Boot), user-service와 연동 |
| user-db | 사용자 정보 저장 MySQL |
| board-db | 게시글 저장 MySQL |

