# 우리 주변 인사이트, 우주인

<img width="1199" alt="image" src="https://github.com/user-attachments/assets/75b61016-5e41-45c3-b362-e18352ee94f9">



## 목차

- 핵심 기능
- 구현 기능 설명
- ERD 다이어그램
- 코카인 팀원 소개
- 배포 링크
- API 명세서
- 기능 명세서
- 에러코드 명세서
- Github 프로젝트



## 핵심 기능


![KakaoTalk_Photo_2024-11-15-18-16-08 002](https://github.com/user-attachments/assets/4ebf209c-293d-4a56-8450-07c99ebca4ac)
![KakaoTalk_Photo_2024-11-15-18-16-08 003](https://github.com/user-attachments/assets/d5562835-c4e8-47fa-9e66-127185a19fb9)
![KakaoTalk_Photo_2024-11-15-18-16-08 004](https://github.com/user-attachments/assets/2f87c050-8be4-4c55-8c94-044679c431e5)
![KakaoTalk_Photo_2024-11-15-18-16-08 005](https://github.com/user-attachments/assets/0650aa88-358f-4725-a5e2-df86bfe6bf8b)
![KakaoTalk_Photo_2024-11-15-18-16-14](https://github.com/user-attachments/assets/6a8c374e-c45e-4930-beed-92260ad55d6f)
![KakaoTalk_Photo_2024-11-15-18-16-17](https://github.com/user-attachments/assets/977c8961-2640-4ca4-85ae-db2cb4826d01)
![KakaoTalk_Photo_2024-11-15-18-16-20](https://github.com/user-attachments/assets/323d36f2-e3de-46bf-a1aa-d21cd2cbc3fe)



## 구현 기능 설명

### 📲 Android

---

#### 멀티모듈

![1](https://github.com/user-attachments/assets/61eaca90-80f2-4b59-bd8f-0b963803678b)

- 처음에는 단순히 너무 많아 정신이 없는 xml 파일을 분리해서 보고 싶어서 시작한 멀티모듈 입니다.
- 저희 앱의 탭바를 기준으로 피처 모듈을 나누고, 핵심 기능과 데이터는 상위 모듈로 구현했습니다.
- 구현은 어려웠지만, 분리된 모듈에서 각자 작업하다 보니 멀티모듈을 적용하기 전보다 충돌 횟수가 현저히 줄어들어 만족했습니다.

---

#### 딥링크를 통한 내비게이션

<img width="667" alt="2" src="https://github.com/user-attachments/assets/1c1a8c5a-d845-4c23-846b-52f091bb0e87">

- 기능 모듈끼리 서로 참조하고 있지 않고 있기 때문에 모듈 간 화면 전환을 할 때 어떻게 해야 할지 막막했습니다.
- 딥링크를 통해서 화면 전환을 구현하기 위해, 모든 기능이 참조 가능한 하위 내비게이션 모듈을 만들어 그 안에 딥링크를 지정하고 화면 전환을 하게 되었습니다.
- 화면 간 전달해야 하는 데이터의 경우, 행사 id, 멤버 id와 같은 간단한 데이터였기에 전달이 용이했습니다.

<img width="843" alt="3" src="https://github.com/user-attachments/assets/88ea7693-610f-4574-be9c-6d393d35f81b">

---

#### 지도 뷰

<img width="1166" alt="image" src="https://github.com/user-attachments/assets/93084b50-0adb-4f8c-8043-17a31556dcdc">

- 사용자의 위치 정보를 바탕으로 실시간으로 주변 정보를 조회하는 기능을 구현했습니다.
- 최대한 과부하를 덜 주기 위해, 
약 20초의 간격을 두고 사용자의 위치를 파악하여 지도에 주변 행사를 마커로 띄우는 것을 구현했습니다.

- 화면 범위 안에만 들어오는 행사를 불러오기 위해, 화면의 시작 위도 경도, 끝 위도 경도를 계산하여 불필요한 데이터 요청을 최소화했습니다.

<br><br><br>

### 🔙 BE

#### Spring Security
   - Spring Security를 활용해 애플리케이션의 인증 및 권한 관리를 구현하였습니다. JWT 기반 토큰 인증과 커스터마이징된 필터를 통해 보안을 강화하였습니다.

#### OAuth(구글 로그인, 카카오 로그인)
   - 구글과 카카오의 OAuth 인증을 통합하여 사용자가 간편하게 소셜 로그인 기능을 이용할 수 있도록 구현하였습니다. 각 플랫폼의 사용자 정보를 통해 회원가입 없이 서비  스를 바로 이용할 수 있게 했습니다.

#### 카카오페이 결제
   - 카카오페이 API를 활용하여 사용자가 앱 내에서도 편하게 결제를 할 수 있도록 구현했습니다.

#### AOP 로깅
   - AOP(Aspect-Oriented Programming)를 활용하여 공통적으로 필요한 로깅 기능을 분리하였습니다. 주요 서비스의 요청, 응답 및 실행 시간을 효과적으로 추적하고, 디버깅 및 모니터링에 활용하였습니다.

#### N+1 문제 해결 (페치 조인 및 배치 사이즈)
   - JPA의 N+1 문제를 방지하기 위해 페치 조인을 적극 활용하고, @BatchSize를 적용하여 필요한 데이터만 효율적으로 조회할 수 있도록 최적화하였습니다.

#### 썸네일 이미지 생성
   - 사용자가 업로드한 이미지를 기반으로 썸네일 이미지를 자동으로 생성하여, 서버 저장 공간과 전송 속도를 최적화하였습니다. 이를 통해 사용자 경험을 개선하였습니다.

#### CI/CD (Docker, GitHub Actions)
   - Docker와 GitHub Actions를 사용하여 CI/CD 파이프라인을 구축하였습니다. 코드 변경 사항이 자동으로 빌드, 테스트, 배포되는 환경을 구성하여 개발 및 배포의 효율성을 높였습니다.



## ERD 다이어그램

![KakaoTalk_Photo_2024-11-15-17-58-55](https://github.com/user-attachments/assets/246353a7-d8e9-49f6-970e-23a23729f7bd)



## 코카인 팀원 소개

### 백엔드

- [전남대 BE 모아림](https://github.com/ariimo/Team26_BE)
- <img src="https://github.com/user-attachments/assets/e32a3a97-d546-43a0-8604-f70e81bf1579" width="200" height="200"/>

- [전남대 BE 조홍식](https://github.com/Daolove0323/Team26_BE)
- <img src="https://github.com/user-attachments/assets/0f5bccd7-e112-421a-833d-034f6d87047f" width="200" height="200"/>

- [전남대 BE 이장안](https://github.com/lja3723/Team26_BE)
- <img src="https://github.com/user-attachments/assets/fb68b420-8d63-41f2-a72c-a2951b810ee7" width="200" height="200"/>

### 안드로이드

- [전남대 Android 이민서](https://github.com/LEEMINSEO00/Team26_Android)
- <img src="https://github.com/user-attachments/assets/2a6f0909-f5db-46c8-8dbf-6cb42742d6a8" width="200" height="200"/>

- [전남대 Android 장수민](https://github.com/sumintnals/Team26_Android)
- <img src="https://github.com/user-attachments/assets/2875ab60-946c-48ec-8820-d561333ec689" width="200" height="200"/>



## 🌐 배포 링크

### 백엔드

https://www.wouldyouin.store

### 안드로이드

https://drive.google.com/file/d/1JzK60wY1RKNoUNFT1CtsWDQZR6l1gE__/view?usp=sharing



## 📖 API 명세서

https://www.notion.so/e8d71c13017844de97303da3c73d0840?v=8432c86ed8114eb1943ff861399fe6c1



## ⚒️ 기능 명세서

https://www.notion.so/67925a6cfccb412191c1e50e958e87f4



## 🚨 에러코드 정의서

https://www.notion.so/fba45247c95d4a129f60a8afddd1228f



## 👨‍👩‍👧‍👦 Github 프로젝트

https://github.com/orgs/kakao-tech-campus-2nd-step3/projects/26
