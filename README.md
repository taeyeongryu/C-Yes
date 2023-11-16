# 🏆 C'YES - 실시간 CS 퀴즈 서비스

C'YES UCC(YOUTUBE) : 
![Imgur](https://i.imgur.com/fTIckoF.png)

<br>
<br>

# 목차
- [프로젝트 진행 기간](#🎞-프로젝트-진행-기간)
- [개요](#✨-개요)
- [주요 기능](#💻-주요-기능)
- [서비스 화면](#🖼-서비스-화면)
- [주요 기술](#🛠-주요-기술)
- [프로젝트 파일 구조](#🗂-프로젝트-파일-구조)
- [프로젝트 산출물](#📋-프로젝트-산출물)
- [팀원 역할 분배](#👩‍💻-팀원-역할-분배)

<br>
<br>

# 🎞 프로젝트 진행 기간

2023.10.10(월) ~ 2023.11.17(금) (39일간 진행)

SSAFY 9기 2학기 자율프로젝트

<br>
<br>

# ✨ 개요




<br>
<br>


# 💻 주요 기능

### 실시간 퀴즈 서비스
- 공지된 시간전까지만 실시간 퀴즈에 참여할 수 있다.


### 문제은행 서비스


### 그룹퀴즈 서비스



<br>
<br>

# 🖼 서비스 화면



## 랭킹

                                                                                         |

## 상세 조회

                                                                         |

## 기업 지원 현황 관리

                                                                            |






<br>
<br>

# 🛠 주요 기술


**Backend**
<br>

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/fastapi-009688?style=for-the-badge&logo=fastapi&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
- Java : Oracle OpenJDK 11.0.17
- SpringBoot 2.7.9
- Spring Data Jpa 2.7.9
- Spring Boot Actuator
- Junit 5.8.2
- Gradle 7.6.1
- MySQL 서버 : latest

<br>

**FrontEnd**
<br>

<img src="https://img.shields.io/badge/nextjs-000000?style=for-the-badge&logo=nextdotjs&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black">&nbsp;<img src="https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=redux&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/styled components-DB7093?style=for-the-badge&logo=styledcomponents&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/node.js-339933?style=for-the-badge&logo=nodedotjs&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/typescript-3178C6?style=for-the-badge&logo=typescript&logoColor=white">&nbsp;

- Next 13.3.0
- React 18.2.0
- Node.js 16.16.0
- TypeScript 5.0.4
- Redux 8.0.5
- Redux-toolkit 1.9.4
- Redux-persist 6.0.0
- Styled-component 5.3.9
- Axios 1.3.5

<br>

**CI/CD**
<br>

<img src="https://img.shields.io/badge/aws ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/openssl-721412?style=for-the-badge&logo=openssl&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">&nbsp;


- AWS EC2
- Ubuntu 20.04 LTS
- Jenkins 2.414.3
- Docker Engine 24.0.5
- Nginx 1.18.0
- SSL
- Grafana latest
- Prometheus 2.44.0

<br>

**협업 툴**
<br>

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jirasoftware&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/figma-EA4335?style=for-the-badge&logo=figma&logoColor=white">&nbsp;
- 형상 관리 : Git
- 이슈 관리 : Jira
- 커뮤니케이션 : Mattermost, Notion
- 디자인 : Figma


<br>
<br>

# 🗂 프로젝트 파일 구조

### Backend

```markdown
backend
|-- 📂domain
|   |-- 📂algorithm
|   |-- 📂analysis
|   |-- 📂github
|   |-- 📂job
|   |-- 📂user
|   |-- 📂util
|   └-- 📂entity
└-- 📂global
    |-- 📂auth
        |-- 📂auth
        └-- 📂config
        └-- 📂exception
        └-- 📂oauth 
        └-- 📂response 

chpo-test
└-- 📂domain
    |-- 📂algorithm
    |-- 📂analysis
    |-- 📂github
    |-- 📂job
    |-- 📂user
    |-- 📂util
    └-- 📂entity 
```

### FrontEnd

```markdown
frontend
|-- 📂components
|   |-- 📂common
|   |-- 📂jobrank
|   |-- 📂login
|   |-- 📂proflie
|   └-- 📂rank
└-- 📂pages
└-- 📂public
└-- 📂redux
└-- 📂styles
└-- 📂utils
    └-- 📂api 

```

<br>
<br>


# 📋 프로젝트 산출물

- [요구사항 명세서]()
- [API 명세서]()
- [ERD]()
- [와이어프레임]()
- [시스템 아키텍처]()




<br>
<br>

# 👩‍💻 팀원 역할 분배

| 우승빈            | 배수빈 | 우수인   | 유혜빈  | 유태영  | 조준희  |
| ----------------- | -------- | -------- | ------- | ------- | ------- |
| <img src="" width="100"> |<img src="https://github.com/ssafy-is-free/free-project/assets/76441040/602edccd-2ad6-4c57-b983-0a8344a0e3a9" width="100">  | <img src="https://github.com/ssafy-is-free/free-project/assets/76441040/84af1b89-9a8d-4b8b-b197-4e8b8ded6425" width="100"> | <img src="" width="100"> | <img src="https://github.com/ssafy-is-free/free-project/assets/76441040/cb25a44e-d63d-4bcc-855d-7558b8051088" width="100">  | <img src="" width="100">  |
| Leader & Backend | Frontend | Frontend | Infra | Backend | Backend |

<br>
<br>
