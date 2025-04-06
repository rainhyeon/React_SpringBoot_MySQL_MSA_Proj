# 빌더 단계
FROM node:18-alpine AS builder

WORKDIR /app
COPY . .

# ✅ OpenSSL 오류 우회 설정
ENV NODE_OPTIONS=--openssl-legacy-provider

RUN npm install
RUN npm run build

# 실행 단계
FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf.template /etc/nginx/templates/nginx.conf.template
# entrypoint 스크립트 복사 및 실행 권한 부여
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# nginx 실행 전 entrypoint 실행되도록 설정
ENTRYPOINT ["/entrypoint.sh"]