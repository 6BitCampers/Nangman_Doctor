<img src="https://github.com/6BitCampers/Nangman_Doctor/assets/86636344/2c29fdd9-7493-4c20-9866-af0f4a72410f" width="100">

<hr>
<br>
<b>낭만닥터!</b><br>
기존 불편했던 기능은 개선하고, <br>
필요한 기능은 추가하고!

## 📃 Detail Role <a name = "role"></a>
이름|담당 기능|Github
---|---|---
김우형|화상진료, 수술예약, Git 관리|[whkim98](https://github.com/whkim98)
배동우|처방전, 리뷰게시판, 결제|[dongwoobae](https://github.com/dongwoobae)
이장우|로그인, 회원가입, QnA|[8282qwe](https://github.com/8282qwe)
이가현|홍보배너, 챗봇, 디자인 총괄|[LeeGaHyun12](https://github.com/LeeGaHyun12)
최시현|검색, 병원 소개|[andychoi0819](https://github.com/andychoi0819)
강하윤|상담예약, SMTP|[hayooniiiiii](https://github.com/hayooniiiiii)
<br/>


<br>

## 개발환경
### FrontEnd
<div>
  <img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=CSS3&logoColor=white">
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white" alt="Thymeleaf Badge">
  <img src="https://img.shields.io/badge/HTML-239120?style=for-the-badge&logo=html5&logoColor=white" alt="HTML Badge">
</div>

### BackEnd
<div>
  <img src="https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=white" alt="Node.js Badge">
  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white"> 
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">
  <img src="https://img.shields.io/badge/mybatis-000000?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black">
  <img src="https://img.shields.io/badge/naver cloud platform-03C75A?style=for-the-badge&logo=naver&logoColor=white">
</div>

### Tools
<div>
  <img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white">
  <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">
  <img src="https://img.shields.io/badge/intellij idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
  <img src="https://img.shields.io/badge/vscode-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white">
</div>

  <br>

---

 <br>

# 📝 [목차](#index) <a name = "index"></a>

- [개요](#outline)
- [프로젝트 구성](#Configuration)
- [다이어그램](#Diagram)
- [요구사항정의서](#definition)
- [WBS](#wbs)
- [협업 방식 - Loop](#Loop)
- [팀의 개발 문화](#culture)
- [ERD](#erd)
- [결과물](#outputs)
- [성과 및 회고](#retrospection)
- [프로젝트 종료 이후 혼자서 진행한 리팩토링](#update)

<br>

# 🎉 개요 <a name = "outline"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br />

<h3>성형외과 전문 통합 시스템 "낭만닥터" !!!</h3>

낭만닥터는 자신의 외모를 가꾸어 나가는 성형의 시대에 맞추어 필요한 정보를 제공하고, 강남에 위치한 모든 성형외과의 정보와 상담 예약까지 제공합니다. 비대면 진료까지 가능한 낭만 닥터의 서비스를 즐겨보세요!


</details>

<br>

# ✨ 프로젝트 구성 <a name = "Configuration"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br />

+ 로그인/회원가입
    - 모든 사용자는 소셜 / 로컬 중 원하는 방식으로 회원가입 및 로그인 가능
    - 소셜로그인(Naver,Kakao,Google)
    - 마이페이지에서 예약 내역 확인 가능

<br>

+ 병원 검색
    - 키워드 검색
    - 검색어 추천
    - 평점 순으로 출력

<br>

+ 예약
    - 병원 검색을 통해 예약 페이지 접속
    - 예약과 동시에 로그인한 이메일 주소로 알림 메일 전송
    - 직원 페이지에서 수락 버튼 클릭시 화상진료 입장 가능

<br>

+ 화상 진료
    - 이메일로 전송된 방이름 입력 후 화상 진료 시작
    - 의사는 처방전 입력 페이지로 이동
    - 채팅 및 카메라 ON/OFF, 마이크 MUTE/UNMUTE
    - 방나가기 버튼 클릭시 화상진료 버튼이 결제 버튼으로 바뀜
      
<br>

+ 결제
    - 의사가 처방전 입력시 결제하러가기 버튼 활성화
    - 카드, 휴대폰, 가상계좌 등의 방법으로 결제 가능
    - 결제시 리뷰쓰러가기 및 처방전 열람 버튼 활성화
      
<br>

+ 챗봇
    - 예약하기, 병원검색 가능
 
<br>

+ 익명게시판
    - 익명으로 작성
    - 작성시 닉네임, 비밀번호, 내용 입력 가능
    - 파일첨부 가능
    - 비밀번호 암호화
 
<br>

+ 직원페이지
    - 의사, 간호사, 행정, 매니저 권한 부여
    - 매니저는 속한 병원의 직원에게 권한 변경 및 병원 정보 수정 가능
    - 캘린더에서 일정 보기
      
<br>
</details>

<br>

# 📈 다이어그램  <a name = "Diagram"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br/>

<h3>ER다이어그램</h3>
<div align="center">
 <img src="https://github.com/6BitCampers/Nangman_Doctor/assets/86636344/01b725d9-fad3-465b-a031-52b6709f58f4" width="450">
</div>

</details>

<br>

# 📋 요구사항 정의서  <a name = "definition"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br />

<div align="center">
 <img src="/images/definition_1.PNG" alt="definition_1">
</div>

<div align="center">
 <img src="/images/definition_2.PNG" alt="definition_2">
</div>

<div align="center">
 <img src="/images/definition_3.PNG" alt="definition_3">
</div>

<div align="center">
 <img src="/images/definition_4.PNG" alt="definition_4">
</div>

</details>

<br>

# 📊 WBS  <a name = "wbs"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br />

<div align="center">
 <img src="/images/wbs.png" alt="wbs">
</div>
</details>

<br>

# 🙌🏻 협업 방식 - Loop <a name = "Loop"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br />


<div align="center">
 <img src="/images/loop_1.PNG" alt="loop_1">
</div>

<div align="center">
 <img src="/images/loop_2.PNG" alt="loop_2">
</div>

<div align="center">
 <img src="/images/loop_3.PNG" alt="loop_3">
</div>

<div align="center">
 <img src="/images/loop_4.PNG" alt="loop_4">
</div>

<div align="center">
 <img src="/images/loop_5.PNG" alt="loop_5">
</div>

<div align="center">
 <img src="/images/loop_6.PNG" alt="loop_6">
</div>

저희 팀은 협업 방식으로 Loop를 사용했습니다.  

아이디어를 공유하고 해당 아이디어 대해 자신의 생각과 추가적인 아이디어를 작성하여 아이디어 보완을 했습니다.

프로젝트 선정 후 목표 우선순위 진행률과 구현할 기능을 선정하고  역할 분담을 하고 각자 맡은 기능들에 대해 백로그를 작성하면서 팀원들과 진행사항을 공유했습니다.

백로그를 통해 개발해야할 모든 기능들, 이번주에 개발해야할 기능, 개발 진행중, 개발 완료된 칸으로 옮기면서 한눈에 볼 수 있도록 진행했습니다. 

멀티캠퍼스에서 진행하는 현업자분들과  멘토링 시간을 가지고 나면, 피드백을 정리하고 이에 대한 회고하고 프로젝트에 반영했습니다.


</details>

<br>


# 🎡 팀의 개발 문화 <a name = "culture"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br />


## 멀개미 Github 규칙
<h3>1. 개발 환경 및 협업 방법:</h3>
각 개발자는 본인이 현재 작업중인 폴더 외의 코드는 확인만 가능하고 직접 수정하지 않습니다.

코드 변경이 필요한 부분을 찾았을 때, 해당 부분의 담당자에게 알려주고 직접 수정하지 않습니다.

<br>

<h3>2. 커밋 규칙:</h3>
매일 최소 1회 이상 커밋합니다.

기능이 완료되지 않은 오류 함수의 경우 주석 처리 후 커밋합니다.

<h3>3.브랜치 전략:</h3>
Main 브랜치에서는 직접적으로 개발하지 않고, 기능 개발은 'feat/내이름' 형식의 Feature 브랜치로 진행합니다.

개발이 완료되면 Feature 브랜치에서 dev 브랜치로 pull request를 생성하여 코드 리뷰를 받고 승인을 받은 후에만 dev 브랜치로 병합(merge)합니다.

dev 브랜치로 merge된 이후에는 변경된 기능을 확인하고, main 브랜치로 merge하기 전에 간단하게 테스트하여 이상이 없을 경우에만 병합합니다.

<h3>4. 브랜치 네이밍:</h3>
개발 기능은 feat/이니셜 형식의 브랜치를 생성합니다.

함수를 생성, 수정, 삭제하는 경우에는 [add], [update], [delete], [add-ing], [update-ing] 등의 태그를 사용하고, 함수명은 method1, method2 등으로 작성합니다. 필요한 경우 상세 내용까지 추가로 기재합니다.


<h3>기타:</h3>
팀 프로젝트 초반에는 스프링 파일 전체 올린 후, 어느정도 완성도가 나오면 중요한 부분만 올리기로 합니다.

초반에 스프링 파일을 전체 올리는 이유는, 매퍼파일 등을 올려야 하기 때문이고,

후반에 중요한 파일만 부분적으로 올리는 이유는, 복잡해서 코드리뷰 하기 힘든 상황을 방지하기 위함입니다.


</details>

<br>


# 📈 ERD <a name = "erd"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br />


<div align="center">
 <img src="/images/erd.PNG" alt="erd">
</div>

</details>

<br>

# 🔎 결과물  <a name = "outputs"></a>

<details>
   <summary> 본문 확인 (👈 Click)</summary>
<br />

배포한 주소입니다.

카카오와 네이버는 사업자등록증이 있어야 인원수 제한 없이 사용가능하여, 구글로 로그인해주시면 됩니다.


https://moneybug.site/moneybug/main.jsp


</details>

<br>


