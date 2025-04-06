#!/bin/sh

# 템플릿을 환경변수로 치환하여 nginx.conf 생성
envsubst '$BOARD_API_HOST $BOARD_API_PORT $USER_API_HOST $USER_API_PORT' < /etc/nginx/templates/nginx.conf.template > /etc/nginx/nginx.conf

# nginx 실행
exec nginx -g 'daemon off;'
