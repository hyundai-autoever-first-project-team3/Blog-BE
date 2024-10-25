🖼️ 블로그 프로젝트 
===

# 🔗 [배포 링크](https://codingcare.site/)
> Coding Care 배포 링크입니다.
# 📌 [API 명세서](https://codingcare.site/api/swagger-ui/index.html)
> API 명세서 입니다.
# 📊 [협업 공간](https://profuse-aftershave-ac6.notion.site/10dfca5223ab80e893b9fd1cd4729399?v=ffffca5223ab81bf8a14000cd77d0810&pvs=4)
> 노션 협업 공간입니다.

# 💻 프로젝트 정보

## 팀원 소개
<table>
<thead>
<tr>
<th align="center"><strong>박준민</strong></th>
<th align="center"><strong>오은솔</strong></th>
<th align="center"><strong>이효원</strong></th>
<th align="center"><strong>고채린</strong></th>
<th align="center"><strong>김대호</strong></th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"><a href="https://github.com/pjm2571"><img src="https://avatars.githubusercontent.com/u/97939207?v=4" height="150" width="150" style="max-width: 100%;"> <br> @pjm2571</a></td>
<td align="center"><a href="https://github.com/OHEUNSOL"><img src="https://avatars.githubusercontent.com/u/74598245?v=4" height="150" width="150" style="max-width: 100%;"> <br> @OHEUNSOL</a></td>
<td align="center"><a href="https://github.com/ymj07168"><img src="https://avatars.githubusercontent.com/u/89841486?v=4" height="150" width="150" style="max-width: 100%;"> <br> @ymj07168</a></td>
<td align="center"><a href="https://github.com/chaelin2"><img src="https://avatars.githubusercontent.com/u/109078051?v=4" height="150" width="150" style="max-width: 100%;"> <br> @chaelin2</a></td>
<td align="center"><a href="https://github.com/DHowor1d"><img src="https://avatars.githubusercontent.com/u/102588838?v=4" height="150" width="150" style="max-width: 100%;"> <br> @DHowor1d</a></td>
</tr>
</tbody>
</table>

[//]: # ()
[//]: # (# 🛠️ 개발 환경)

[//]: # (## 기술 스택 [Backend])

[//]: # ()
[//]: # (<h2>백엔드 기술 스택</h2>)

[//]: # (<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/jpa%20%26%20queryDSL-6DB33F?style=for-the-badge&logo=hibernate&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">)

[//]: # ()
[//]: # (<h2>프론트 기술 스택</h2>)

[//]: # (<img src="https://img.shields.io/badge/svelte-FF3E00?style=for-the-badge&logo=svelte&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/tailwind%20css-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">)

[//]: # (<img src="https://img.shields.io/badge/Portone-000000?style=for-the-badge&logo=none&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/kakao%20map-FFCD00?style=for-the-badge&logo=kakao&logoColor=black">)

[//]: # ()
[//]: # ()
[//]: # (<h2>배포 기술 스택</h2>)

[//]: # (<img src="https://img.shields.io/badge/aws_ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/github%20actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">)

[//]: # ()
[//]: # (<h2>버전 관리</h2>)

[//]: # (<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">)

[//]: # ( </div>)

[//]: # ()
[//]: # (<h2>협업툴</h2>)

[//]: # (<img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">)

[//]: # ()
[//]: # (<h2>디자인</h2>)

[//]: # (<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">)

# ⌨️ Sequence Diagram

## Nginx Reverse Proxy 흐름도
```mermaid
sequenceDiagram
    participant Client
    participant Nginx
    participant Frontend as FrontPage
    participant Backend as Backend Server

    Client ->> Nginx: codingcare.site/
    Nginx ->> Frontend: 정적 파일 요청
    Frontend -->> Nginx: 정적 파일 반환
    Nginx -->> Client: 정적 페이지 제공

    Client ->> Nginx: codingcare.site/api
    Nginx ->> Backend: API 요청 전달
    Backend -->> Nginx: API 응답 반환
    Nginx -->> Client: API 응답 전달

```
## Login 인증 Sequence
```mermaid
sequenceDiagram
    participant User
    participant WebApp as Web Application
    participant AuthService as Auth Service
    participant Database

    User ->> WebApp: 로그인 요청
    WebApp ->> AuthService: 자격 증명 확인 요청
    AuthService ->> Database: 사용자 정보 조회
    Database -->> AuthService: 사용자 정보 반환
    AuthService -->> WebApp: 인증 결과 반환
    WebApp -->> User: 로그인 성공/실패 알림
```

## 
