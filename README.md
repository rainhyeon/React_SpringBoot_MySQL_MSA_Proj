## 스프링 3.x 버전으로 따라하시는 분들
```text
스프링 3.x 버전은 최소 JDK 버전이 17입니다.
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

