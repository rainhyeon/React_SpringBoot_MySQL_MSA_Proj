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


## docker compose 실행
```code
cd 0326_Docker_Toyproj
docker compose up -d --build
```
### docker compose로 frontend 만 Build 및 실행
```code
cd 0326_Docker_Toyproj
docker compose build frontend
docker compose up frontend -d
```

### docker compose로 user-service 만 Build 및 실행
```code
cd 0326_Docker_Toyproj
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


## 접속 방법
```text
http://{localhost}:3000 으로 접속
```



