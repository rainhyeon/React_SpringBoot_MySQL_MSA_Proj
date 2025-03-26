# 빌더 단계
FROM rainhyeonnn/node:18-alpine AS builder

WORKDIR /app
COPY . .

# ✅ OpenSSL 오류 우회 설정
ENV NODE_OPTIONS=--openssl-legacy-provider

RUN npm install
RUN npm run build

# 실행 단계
FROM rainhyeonnn/nginx:stable-alpine
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
