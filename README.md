## 스프링 3.x 버전으로 따라하시는 분들
```text
스프링 3.x 버전은 최소 JDK 버전이 17입니다.
```

## Docker 설치 - ubuntu 환경
```code
curl -fsSL https://get.docker.com -o get-docker.sh
chmod +x get-docker.sh
./get-docker.sh
```
![image](https://github.com/user-attachments/assets/c8140f35-89e6-43db-8f39-acf2b8eb492f)


### Docker 설치 확인
```code
ip add
```


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
```text
http://{localhost}:3000 으로 접속
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


