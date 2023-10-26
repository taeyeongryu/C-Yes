# # 가져올 이미지를 정의
# FROM node:14
# # 경로 설정하기
# WORKDIR /app
# # package.json 워킹 디렉토리에 복사 (.은 설정한 워킹 디렉토리를 뜻함)
# COPY package.json .
# # 명령어 실행 (의존성 설치)
# RUN npm install
# # 현재 디렉토리의 모든 파일을 도커 컨테이너의 워킹 디렉토리에 복사한다.
# COPY . .

# # 각각의 명령어들은 한줄 한줄씩 캐싱되어 실행된다.
# # package.json의 내용은 자주 바뀌진 않을 거지만
# # 소스 코드는 자주 바뀌는데
# # npm install과 COPY . . 를 동시에 수행하면
# # 소스 코드가 조금 달라질때도 항상 npm install을 수행해서 리소스가 낭비된다.

# # 3000번 포트 노출
# EXPOSE 3000

# # npm start 스크립트 실행
# CMD ["npm", "start"]

# # 그리고 Dockerfile로 docker 이미지를 빌드해야한다.
# # $ docker build .

# # Build Stage
# FROM node:14 as build
# WORKDIR /app
# COPY package*.json ./
# RUN npm install
# COPY . .
# RUN npm run build


# # Production Stage
# FROM nginx:1.21-alpine
# COPY --from=build /app/build /usr/share/nginx/html
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]


# 기본 이미지로 Node.js를 사용
FROM node:14 as build
# 작업 디렉토리 설정
WORKDIR /app
# 의존성 파일 복사
COPY package.json package-lock.json ./
# 의존성 설치
RUN npm install
# 소스 코드 복사
COPY . .
# 애플리케이션 빌드
RUN npm run build

# Nginx를 사용하여 React 애플리케이션 서빙
FROM nginx:alpine
# React 빌드 결과물을 Nginx 서버로 복사
COPY --from=build /app/build /usr/share/nginx/html
# 커스텀 Nginx 설정 파일 복사
COPY nginx/custom.conf /etc/nginx/conf.d/default.conf
# 포트 80 노출
EXPOSE 80
# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]

