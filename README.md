## 📖 1. 프로젝트 소개

- 모바일로봇 테미는 디스플레이가 탑재된 이동가능한 로봇입니다.
- 테미를 활용해 사무실 업무를 보조할 수 있는 시나리오를 구상하고, 로봇 전용 안드로이드 앱을 개발했습니다.

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/user-attachments/assets/55384c96-cc87-4529-9ec2-578f343b36d3" alt="테미 깃허브 표지" width="48%" />
  <img src="https://github.com/user-attachments/assets/a1944ec9-7e3b-4596-86cd-3cc2f6a94250" alt="시나리오" width="48%" />
</div>

## 🎭 2. 역할
- IoT 시스템 구현 - 미세 먼지, 온습도 등 환경 데이터 감지 및 서버 전송 기능 개발
- 서버에서 수신한 테미 상태 정보에 따라 네오픽셀 조명 제어 시스템 구축
- 서류함 개방 명령 수신 시 서보모터 작동을 통한 자동 서류함 개방 메커니즘 구현
- MVVM 구조로 애플리케이션 아키텍처 최적화하여 시스템 성능 향상

## 🛠️ 3. 개발 환경

### 🔍 1) 프레임워크 및 언어
- Front-end: Java 8 (1.8)
- Back-end: Real-time Firebase (29.3.1)

### 🔧 2) 개발 도구
- Android Studio: Meerkat | 2024.3.1
- Gradle: 8.10.2
- AGP: 8.8.2
- JDK: 11

### 📱 3) 테스트 환경
- Android 에뮬레이터: API 레벨 32 (Android 12L)
- Mobile Robot Temi: API 레벨 32 (Android 12L)

### 📚 4) 주요 라이브러리 및 API
- robotemi:sdk (1.135.1) (Temi 로봇 제어용 SDK)
- glide (4.16.0) (이미지 관리)
- firebase-bom (33.10.0) (Firebase 관련)
- firebase-analytics
- firebase-database

### 🔖 5) 프로젝트 관리
- 버전 관리: Git (2.39.5)
- 커뮤니케이션: Kakaotalk, Zoom

### ☁️ 7) 서비스 배포 환경
- 백엔드 서버: Firebase Realtime Database
- 배포 방식: Firebase Cloud 호스팅

## ▶️ 4. 프로젝트 실행 방법

### ⬇️ 1) 필수 설치 사항

#### ① 기본 환경
- Android Studio (최신 버전)
- Java JDK (Java 8 이상)
  - Android SDK (minSdk 24, targetSdk 35)
- Google Play 서비스 SDK

#### ② 필수 의존성 패키지
- androidx.appcompat:appcompat: 1.7.0
- com.google.android.material:material: 1.12.0
- androidx.constraintlayout:constraintlayout: 2.2.1

### ⿻ 2) 프로젝트 클론 및 설정
- 프로젝트 클론
```bash
git clone https://github.com/sorongosdev/NewOfficeTemiApp.git
```
- 의존성 설치
```bash
# Mac
./gradlew --refresh-dependencies

# Window
gradlew.bat --refresh-dependencies
```

### 🌐 3) 앱 빌드
```bash
./gradlew build
```

## 📁 5. 프로젝트 구조
```
newofficetemiapp
├── data
│   ├── model
│   │   ├── DeliveryStatus.java
│   │   ├── Location.java
│   │   └── User.java
│   ├── repository
│   │   ├── FirebaseRepository.java
│   │   ├── TemiRepository.java
│   │   └── UserRepository.java
├── temi
│   ├── RoboTemiService.java
│   └── TemiNavigationHelper.java
├── ui
│   ├── base
│   │   ├── BaseActivity.java
│   │   ├── BaseFragment.java
│   │   ├── BaseViewModel.java
│   │   └── NavBaseViewModel.java
│   ├── call
│   │   ├── CallActivity.java
│   │   └── CallViewModel.java
│   ├── delivery
│   │   ├── CardReceiveActivity.java
│   │   ├── CardSendActivity.java
│   │   ├── CompletionActivity.java
│   │   ├── DeliveringActivity.java
│   │   ├── DeliveryActivity.java
│   │   ├── DeliveryAdapter.java
│   │   ├── DeliveryStatusActivity.java
│   │   ├── DeliveryViewModel.java
│   │   └── PullActivity.java
│   ├── firebase
│   │   ├── FirebaseActivity.java
│   │   └── FirebaseViewModel.java
│   ├── hello
│   │   ├── HelloActivity.java
│   │   └── HelloViewModel.java
│   ├── location
│   │   ├── LocationActivity.java
│   │   ├── LocationMapFragment.java
│   │   └── LocationViewModel.java
│   ├── main
│   │   ├── MainActivity.java
│   │   └── MainViewModel.java
│   ├── meeting
│   │   ├── MeetingActivity.java
│   │   └── MeetingViewModel.java
│   ├── menu
│   │   ├── MenuActivity.java
│   │   └── MenuViewModel.java
│   └── patrol
│       ├── PatrolActivity.java
│       └── PatrolViewModel.java
└── util
    ├── Constants.java
    └── RobotUtils.java

```

## 📅 6. 개발 기간

- 전체 개발 기간: 2021.10 ~ 2021.12
- 기획 및 디자인: 2022.09 ~ 2022.10
- 개발: 2022.11 ~ 2022.12
- 리팩토링: 2025.03

## 📜 7. 기능 소개

### 📄 1) 서류 전달

- 테미를 호출한 후, RFID 카드로 본인 인증을 합니다.
- 목적지(수령인)를 설정하고, 서류를 전달합니다.
- 수령인에게 도착한 테미는 본인 인증을 완료한 후 서류함의 잠금을 해제하고, 서류를 전달합니다.

| 발신인 서류함에 서류 삽입 | 발신인 태그 |
| :---: | :---: |
| <img width="1724" alt="발신인 서류함에 서류 삽입" src="https://github.com/user-attachments/assets/9096d12e-00a2-42bd-af70-10f01bd8cfe7" /> | <img width="1722" alt="발신인 태그" src="https://github.com/user-attachments/assets/ebad7fb2-a075-42bc-a1f9-d71b0ea76ca1" /> |

| 수신인에게 도착 | 수신인 인증 후 수령 |
| :---: | :---: |
| <img width="1722" alt="수신인에게 도착" src="https://github.com/user-attachments/assets/8e3b1f70-4a4a-49b3-8b82-0928f05db6e1" /> | <img width="1717" alt="수신인 태그" src="https://github.com/user-attachments/assets/cd41980b-10ca-4825-a380-704b143dee92" /> |

### 📄 2) 군집 제어

- 테미를 호출할 때 호출한 사람과 가장 가까운 테미가 호출됩니다.
- 다른 사람이 이용 중인 테미는 호출되지 않습니다.

| 순찰 중 | 가장 가까운 테미가 호출자 앞으로 옴 | 
| :---: | :---: |
| <img width="1722" alt="순찰" src="https://github.com/user-attachments/assets/a60adfec-79ed-4d50-a1a9-cb8aadf8dfaf" /> | <img width="1715" alt="앞으로 옴" src="https://github.com/user-attachments/assets/391f64c6-a99e-4dea-bc6a-257f5df4474b" /> |

### 📄 3) 회사 알리미

- 사내 식당 메뉴를 확인할 수 있습니다.
- 회의 일정을 확인할 수 있습니다.

| 회사 알리미 | 순찰 중인 테미에게 접근 |
| :---: | :---: |
|  <img width="1728" alt="스크린샷 2025-03-13 오후 5 41 52" src="https://github.com/user-attachments/assets/58401879-78da-43ed-acfe-1445e9b3e1b3" /> | <img width="1728" alt="순찰 중인 테미에게 접근" src="https://github.com/user-attachments/assets/595d6d1d-468d-42b9-8774-1432bc2e4aa8" /> |  | 

| 테미를 터치 | 테미가 고개를 들고 옵션 제시 |
| :---: | :---: |
| <img width="1728" alt="테미를 터치" src="https://github.com/user-attachments/assets/c867b2cd-d076-4059-bc81-1235cf91927c" /> | <img width="1728" alt="테미가 고개를 들고 옵션 제시" src="https://github.com/user-attachments/assets/2b07defb-1dd3-412a-8749-f70f04f0eed9" /> |

### 📄 4) 쾌적한 사무실

- 넓은 사무실의 경우, 가습기와 공기청정기가 고정되어 있으면 전체 환경이 쾌적해지긴 어렵습니다.
- 테미는 환경감지 센서(온습도, 미세먼지 센서)를 부착하고 사무실을 돌아다니며, 가습기와 공기청정기를 원격으로 제어합니다.

| 평상시 | 미세먼지, 습도 이상 감지 |
| :---: | :---: |
| <img width="1728" alt="평상시" src="https://github.com/user-attachments/assets/bee2d0db-72cb-429f-adcd-3d89f21ed9eb" /> | <img width="1728" alt="미세먼지, 습도 이상" src="https://github.com/user-attachments/assets/f964f8f2-09b3-46bf-ba86-9a9acdfa3fa9" /> |

### 📄 5) 방문객 안내

- 방문객이 오면 찾는 사람에게 안내합니다.

| 방문객 안내 | 방문객 등장 |
| :---: | :---: |
| <img width="1728" alt="스크린샷 2025-03-13 오후 5 42 03" src="https://github.com/user-attachments/assets/9ef80889-8543-42d8-8ae2-37e8b0edf9df" /> | <img width="1728" alt="방문객 등장" src="https://github.com/user-attachments/assets/0a209ac9-d78a-4bea-b842-a3ae80b397e9" /> |


| 방문객이 방문 대상 선택 | 방문 대상에게로 안내 |
| :---: | :---: |
| <img width="1727" alt="방문객이 방문 대상 선택" src="https://github.com/user-attachments/assets/f0ddf281-9a60-4514-a46f-104b10b47133" /> | <img width="1796" alt="방문 대상으로 안내" src="https://github.com/user-attachments/assets/e2674d66-2b0a-4f09-b978-32c144137297" /> |

## 🏛️ 7. 발표자료 및 논문

- ![사무 업무 보조 로봇 테미 발표 자료](https://github.com/user-attachments/files/19240148/default.pdf)

- ![모바일 로봇을 활용한 사무 업무 보조 서비스 개발.pdf](https://github.com/user-attachments/files/19240149/default.pdf)

