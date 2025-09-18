## 프로젝트 개요
- 기존 모놀리식(Springboot-JPA-Blog) 구조를 MSA 아키텍처로 전환
- React (Frontend), Spring Boot (Backend), MySQL (DB) 구성
- Docker 기반으로 각 서비스 컨테이너화 및 배포 자동화
- API 중심 설계로 프론트/백엔드 완전 분리

<img width="882" height="594" alt="image (63)" src="https://github.com/user-attachments/assets/18cf9e9e-4f81-4c4c-be5e-d3c8e0119b6d" />

- Frontend: React SPA, Nginx 정적 파일 서빙 및 API 프록시
- User-service: 회원가입, 로그인, 사용자 관리
- Board-service: 게시글 CRUD
- Databases: 각 서비스별 독립 MySQL DB

## 주요 기능
- 회원가입 / 로그인 (user-service + MySQL)
- 게시글 작성 / 조회 (board-service + MySQL)
- React 프론트엔드 → Axios → Nginx → Spring Boot API 연동
- Docker Compose 기반 멀티 컨테이너 환경 실행

## 트러블 슈팅
- Spring Boot/Java 버전 불일치 → Boot 2.x → 3.x 업그레이드
- JAR 빌드 시 이름 불일치 → pom.xml의 artifactId 수정
- 서비스 포트 충돌 → Docker 포트 매핑 수정
- DB 연결 오류 → Docker 네트워크 공유 및 hibernate.dialect 설정 추가
- Nginx 404 에러 → try_files $uri /index.html; 및 /api/ proxy 설정
- React Build 시 OpenSSL 오류 → ENV NODE_OPTIONS=--openssl-legacy-provider 적용


## Docker 설치 - ubuntu 환경
```code
curl -fsSL https://get.docker.com -o get-docker.sh
chmod +x get-docker.sh
./get-docker.sh
```


### Docker 설치 확인
```code
ip add
```
![image](https://github.com/user-attachments/assets/c8140f35-89e6-43db-8f39-acf2b8eb492f)


## docker compose로 빌드 및 실행
```code
mv React_SpringBoot_MySQL_MSA_Proj WebSite
cd WebSite
docker compose up -d --build
```

### docker compose로 frontend 만 Build 및 실행
```code
cd WebSite
docker compose build frontend
docker compose up frontend -d
```

### docker compose로 user-service 만 Build 및 실행
```code
cd WebSite
docker compose build user
docker compose up user -d
```

### docker compose로 user-db 만 Build 및 실행
```code
cd 0326_Docker_Toyproj
docker compose build user-db
docker compose up user-db -d
```

### docker log 확인 - frontend
```code
docker ps # NAME 확인
docker logs {frontend NAME}
```

## Docker ps 확인
- 포트 확인
![image](https://github.com/user-attachments/assets/b97e427e-4265-4a41-8fc0-00993166bd9b)

### 컨테이너 역할
- user: 로그인/회원가입
- user-db: 회원 정보 저장 DB (Database: users, Table: users)
- board: 게시판 글쓰기
- board-db: 게시판 글 저장 DB (Database: board_db, Table: board)

## 웹 접속 방법
- Frontend
```text
http://{localhost}:3000
```
- User API
```text
http://{localhost}:8081/api/users
```
- Board API
```text
http://{localhost}:8082/api/board
```
### HomePage
![image](https://github.com/user-attachments/assets/1be89b40-263d-47e3-93b9-c5aa224e5180)

### LoginPage
![image](https://github.com/user-attachments/assets/01e2db36-9afb-479a-9372-e01fe23cdafb)

### RegisterPage
![image](https://github.com/user-attachments/assets/9f527fc0-00bc-4099-a188-bf5735875658)

### WritePage
![image](https://github.com/user-attachments/assets/512e7360-f432-4e68-a3ab-b757e2f53bd1)
- 꼭 회원가입한 username으로 사용자 ID를 작성해야한다


## DB에 들어간 데이터 내용 확인
### user-db 확인
```code
docker ps # user-db NAME 확인
docker exec -it {user-db NAME} sh
> mysql -u root -ppassword # mySQL 접속
> SHOW DATABASES; # DB 명 확인
> use users; # DB 선택
> SHOW TABLES; # 테이블 명 확인
> select * from users; # 테이블에 저장된 데이터 확인 가능
```

### board-db 확인
```code
docker ps # board-db NAME 확인
docker exec -it {board-db NAME} sh
> mysql -u root -ppassword # mySQL 접속
> SHOW DATABASES; # DB 명 확인
> use board_db; # DB 선택
> SHOW TABLES; # 테이블 명 확인
> select * from board; # 테이블에 저장된 데이터 확인 가능
```


