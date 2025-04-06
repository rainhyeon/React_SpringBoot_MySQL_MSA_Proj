## 📌 프로젝트 개요

- 기존 모놀리스(Springboot-JPA-Blog) 구조를 MSA 아키텍쳐로 전환하여, 서비스별 독립 개발 및 배포 가능하도록 구성
- **React (Frontend)**, **Spring Boot (Backend)**, **MySQL (DB)**로 서비스 분리
- 개발 및 테스트 환경에서는 **Docker Compose**로 로컬 통합 실행
- 실제 배포 환경에서는 **AWS ECS + ECR** 기반으로 운영 가능하도록 구조 개정
- nginx 프로시 설정을 환경변수 기반으로 템플릿화하여, 다양한 배포 환경(ECS, EC2 등)에서도 재사용 가능
- API 중심 \uuc124계로 프롬트/백어드 완전 분리 및 확장성 확률

---

## 💠 Docker 설치 (Ubuntu 기준)

```bash
curl -fsSL https://get.docker.com -o get-docker.sh
chmod +x get-docker.sh
./get-docker.sh
```

---

## 🐳 전체 서비스 실행

```bash
git clone https://github.com/rainhyeon/React_SpringBoot_MySQL_MSA_Proj.git
mv React_SpringBoot_MySQL_MSA_Proj WebSite
cd WebSite
docker compose up -d --build
```

---

## ⚙️ nginx 프로시 구성 (frontend)

React에서 API 요청은 `/api/users/*`, `/api/board/*` 경로로 보내며, nginx는 다음과 같이 backend에 프로시화함:

```
/api/users  →  user-service
/api/board  →  board-service
```

그리고 프로시 주소는 환경변수 기반으로 설정되어, Docker 환경 및 ECS 배포에서도 재사용 가능해지는 구조로 개정되었음.

> `nginx.conf`는 `/frontend/nginx.conf.template`에서 환경값 `${USER_API_HOST}`, `${BOARD_API_HOST}`를 템플릿 컨퍼에스트로 변경 적용해 생성됩니다.

---

## 🧱 Dockerfile 구조 (frontend)

```dockerfile
# 1단계: React 앱 빌드
FROM node:18-alpine AS builder
WORKDIR /app
COPY . .
ENV NODE_OPTIONS=--openssl-legacy-provider
RUN npm install
RUN npm run build

# 2단계: nginx + 프로시 설정
FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf.template /etc/nginx/templates/nginx.conf.template
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
```

> `entrypoint.sh`는 환경값을 받아 nginx.conf를 디바이너리에 생성하는 역할을 합니다.

---

## ⚙️ 환경변수 설정 예시 (docker-compose)

```yaml
  frontend:
    ...
    environment:
      BOARD_API_HOST: board-service
      BOARD_API_PORT: 8080
      USER_API_HOST: user-service
      USER_API_PORT: 8081
```

---

## 🧪 서비스별 빌드 및 실행

### 프롬트엔드
```bash
docker compose build frontend
docker compose up frontend -d
```

### 유저 서비스
```bash
docker compose build user-service
docker compose up user-service -d
```

### 게시판 서비스
```bash
docker compose build board-service
docker compose up board-service -d
```

---

## 🔍 로그 확인 (frontend 기준)

```bash
docker ps      # 컨테이너 이름 확인
docker logs ecr_prj-frontend-1
```

---

## 🌐 웹 접속 방법

```
http://localhost:3000
```

- 회원가입, 로그인, 글쓰기 등 React 기본 UI 제공
- API 호출은 내부 nginx 프로시를 통해 backend로 보내지보

---

## 📄 MySQL DB 확인

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

## 📦 컨테이너 구조 요약

| 서비스 | 설명 |
|------------|--------|
| frontend | React 정적 파일 + nginx 프로시 서버 |
| user-service | 회원가입/로그인 API (Spring Boot) |
| board-service | 게시판 API (Spring Boot) |
| user-db | 사용자 정보 저장 MySQL |
| board-db | 게시물 저장 MySQL |
