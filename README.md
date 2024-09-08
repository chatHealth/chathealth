# 🌿 ChatHealth 🌿

> ### 🧑‍💻 프로젝트 소개
> 
> ---
>
> <img style="background-color: #FFF" width="786" alt="Main Logo" src="https://github.com/user-attachments/assets/08513f01-fe78-4fe8-99f8-c4e8696714c4">
> 
> ---
> 
> * "대한민국, 건강을 말하다". 정말로 나에게 필요한 건강기능식품을 찾아갈 수 있도록 돕는 플랫폼입니다. 현재 나의 상태를 기반으로 어떤 성분이 내 몸에 도움이 되는지 알아보고, 다른 사람들의 생생한 후기까지 함께 확인할수 있는 애플리케이션의 프로토타입을 구현하였습니다.
> * 건강기능식품을 찾아보고 싶은 사용자들을 위해 건강기능식품 정보를 제공하고, 사용자들이 건강기능식품에 대한 후기를 남길 수 있는 커뮤니티를 제공합니다.
> * 사용자들이 건강기능식품 정보를 찾아보고, 후기를 남기며 서로 소통할 수 있는 플랫폼을 제공합니다.
> * 커뮤니티의 활성화를 위해 사용자들이 자유롭게 글을 작성하고, 쪽지 및 채팅 기능을 통해 소통할 수 있도록 합니다.


> ### 🕰️ 개발 기간
> 
> ---
> 
> * 2024.01.05 ~ 2024.02.19


> ### 📌 주요 기능
> 
> ---
> 
> |                                              <b>메인 페이지</b>                                               |                                               <b>회원가입</b>                                                |
> |:--------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|
> | <img src="https://github.com/user-attachments/assets/831ce6f8-fa12-4451-9b20-03ca8b292ed9" width="300"/> | <img src="https://github.com/user-attachments/assets/229d83c8-d2f2-4ba1-9d18-ac1aed4aeee4" width="300"/> |
> |                                                <b>로그인</b>                                                |                                            <b>제품 조회 및 검색</b>                                             |
> | <img src="https://github.com/user-attachments/assets/c8e0a407-043e-48d3-8726-f1228f76e125" width="300"/> | <img src="https://github.com/user-attachments/assets/50a7bcb8-04e5-4fbc-9f69-464fe8d43421" width="300"/> |
> |                                            <b>제품 상세 및 리뷰</b>                                             |                                         <b>커뮤니티 글 목록 조회 및 검색</b>                                         |
> | <img src="https://github.com/user-attachments/assets/2d87e0b7-a052-4f78-adc8-873a1ff16ef9" width="300"/> | <img src="https://github.com/user-attachments/assets/29151974-613f-41d0-b4c3-333abb541754" width="300"/> |
> |                                          <b>커뮤니티 글 조회 및 댓글</b>                                           |                                              <b>쪽지 수•발신</b>                                              |
> | <img src="https://github.com/user-attachments/assets/44d7e7e2-2e63-4ab5-90e1-8c4c01e4a558" width="300"/> | <img src="https://github.com/user-attachments/assets/8c85aa1f-bcd3-40ef-9426-af0ab2b49103" width="300"/> |
> |                                               <b>익명 채팅</b>                                               |                                              <b>마이 페이지</b>                                               |
> | <img src="https://github.com/user-attachments/assets/756c9ba9-b93f-4942-a648-ef2d89d7f311" width="300"/> | <img src="https://github.com/user-attachments/assets/1f494b46-a4f0-418e-9589-ae9d6c36d61a" width="300"/> |



> ### 👪 개발자 소개
> 
> ---
> 
> * 배찬용 : DB설계, 회원 관리, 발표
> * 정규선 : 건강기능식품 게시판, 데이터 조사
> * 조원우 : 깃허브 관리, 건강기능 식품 기능, 커뮤니티, 쪽지•채팅 기능, 서버 생성 및 배포


> ### 💻 기술 스택
> 
> ---
> 
> <img width="1968" alt="Tech Stack" src="https://github.com/chatHealth/chathealth/assets/140994218/ce303a9b-d3ca-44ab-8805-32cae7b9cae6">


> ### 🛠️ 협업 툴
> 
> ---
> 
> <img width="890" alt="cooperative tools (1)" src="https://github.com/chatHealth/chathealth/assets/140994218/96ffbe1b-84a9-4960-8579-50eda35fb536">


> ### 🌵 Git Flow
> 
> ---
> 
> ```
> main
> ├── dev
> │   └── feature
> └── hotfix
> ```
> * ***main***: 배포용 브랜치. 모든 기능이 정상적으로 동작하는 상태만 병합
> * ***dev***: 개발 브랜치. 각 기능별로 브랜치를 생성하여 병합. 테스트 후 main 브랜치로 병합
> * ***feature***: 특정 기능을 개발하는 브랜치. 각 기능 완성시 dev 브랜치로 병합
> * ***hotfix***: 배포 후 발생한 버그를 수정하는 브랜치. main 브랜치로 병합


> ### 📝 Convention
> 
> ---
> 
> * Commit Message<br>
>> ```Feat```: 새로운 기능 추가<br>
> ```Fix```: 버그 수정<br>
> ```Hotfix```: 급한 버그 수정<br>
> ```Test```: 테스트 코드 작성<br><br>
> ```Design```: CSS 등 디자인 변경<br>
> ```Refactor```: 코드 리팩토링<br>
> ```Style```: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우<br>
> ```Docs```: 문서 변경<br><br>
> ```Chore```: 빌드 업무 수정, 패키지 매니저 수정<br>
> ```Comment```: 필요한 주석 추가 및 변경<br>
> ```Rename```: 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우<br>
> ```Remove```: 파일을 삭제하는 작업만 수행한 경우
> 
> * 디렉토리 구조<br>
>> **디렉토리명**: UnderCase로 작성 (ex.) ```main/java/config```<br>
> **자바 파일명**: PascalCase로 작성 (ex.) ```main/java/config/WebConfig.java```<br>
> **페이지 파일명**: kebab-case로 작성 (ex.) ```main/resources/templates/auth/user-join.html```<br>
> 
> * 변수, 상수<br>
>> **변수명**: camelCase로 작성 (ex.) ```int chatHealth = 1000```<br>
> **상수명**: 대문자로 작성 (ex.) ```final int CHAT_HEALTH = 1000```<br>
> 
> * Milestone, Issue<br>
>> **Milestone**: 각 화면 구상도에 따른 화면 별로 마일스톤<br>
> **Issue**: 각 마일스톤에 따라 기능 별로 이슈 생성<br>
> **Checklist**: 각 이슈에 대해 더 세세하게 체크리스트 활용<br>


> ### 📚 ERD
> 
> ---
> 
> <img alt="ERD Diagram" src="https://github.com/user-attachments/assets/a57f22cd-2975-4289-8197-81c1eb29bd36">


> ### 🚀 기술적 도전과 해결
> 
> ---
> 
> #### 1. **권한**
> 사용자의 권한이 여러 가지로 나뉘어져 있어서 이를 어떻게 관리할지 고민을 하였습니다.
> * **DB설계시**: 데이터베이스 상에서 사용자의 권한을 어떻게 관리할지에 대한 고민. 서비스의 규모가 크지 않다고 판단을 하였으므로 단일 테이블 전략을 택하여 권한을 관리하는 컬럼을 추가하여 구분.
> * **접근제어**: 사용자의 접근 권한 관리를 위해 spring security를 사용하여 filter chain을 이용하여 접근 권한을 관리.
> 
> #### 2. **쪽지 알림**
> 사용자가 쪽지를 받았을 때 알림을 주는 방법에 대한 고민. 처음에는 Polling 방식을 사용하였으나, 이는 서버에 부하를 주는 문제가 발생. 그래서 SSE(Server-Sent Events)를 사용하여 실시간으로 알림을 주는 방식으로 변경.
> * Polling 방식: 처음에는 클라이언트에서 15초마다 서버에 접근을 하여 새로운 쪽지가 있는지 여부를 확인하여 쪽지를 받도록 하였음. <br>하지만 서버측에서 전송할 정보가 없음에도  클라이언트 측에서 지속적으로 request를 보내는 문제가 발생.
> * SSE(Server-Sent Events): 서버에서 클라이언트로 실시간으로 데이터를 전송하는 기술로, 클라이언트에서 서버로 요청을 보내고 서버에서 클라이언트로 데이터를 보내는 방식으로 구현.<br> 이를 통해 서버에서 새로운 쪽지가 있을 때 클라이언트로 알림을 주는 방식으로 변경.<br> 단방향 프로토콜을 사용하기에 Polling 방식보다 서버에 부하를 덜 주는 방식으로 변경.
> 
> #### 3. **채팅**
> 채팅을 구현하는 과정에서 Polling 방식과 SSE 방식은 실시간의 양방향 통신을 위한 방법이 아니라는 것을 깨달음. 그래서 WebSocket을 사용하여 실시간 양방향 통신을 구현.
> * WebSocket: HTTP 프로토콜을 사용하는 웹페이지에서 서버와 실시간으로 양방향 통신을 할 수 있도록 하는 기술로, 클라이언트와 서버가 지속적으로 연결을 유지하고 있어야 하기 때문에 Polling 방식이나 SSE 방식보다 서버에 부하를 더 주는 방식이지만, 실시간 양방향 통신을 위한 방법으로 채택.<br> Java를 사용하기에 SockJs와 Stomp를 사용하여 WebSocket을 구현.
> 
> #### 4. **복잡한 쿼리**
> 사용자가 검색 조건 필터링을 통해 원하는 정보를 찾을 수 있도록 하기 위해 복잡한 쿼리를 작성해야 했음. 그런데 join을 하는 횟수가 많아 지기에 N+1 문제가 발생. 이를 해결하기 위해 fetch join을 사용하여 한 번에 모든 데이터를 가져오도록 함. 그런데 또 거기에서 발생하는 대용량 데이터를 가져오는 데 시간이 지나치게 오래 걸림. 그에 따른 해결책으로 Batch Size를 설정하여 한 번에 가져오는 데이터의 양을 조절하여 성능을 향상시킴.
> * Batch Size: ```@BatchSize(size = 1000)```를 사용하여 한 번에 가져오는 데이터의 양을 조절하여 성능을 향상시킴.
>
> #### 5. **테스트 코드**
> 테스트 코드의 필요성을 느끼고 작성하였음. 테스트 코드를 작성하지 않음으로 인해 빌드 이후 오류를 찾는데 시간이 오래 걸리고, 테스트를 진행하지 않은 코드에 대한 버그가 발생하여도 빠르게 찾지 못하는 문제가 발생. 그래서 단위 테스트와 통합 테스트를 JUnit5와 Mockito를 사용하여 작성하였음.
