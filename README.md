# Daily Diary

## 💡 개요

> #### "하루 한 번, 일상을 기록하는 웹"
 
## ✨ 핵심 기능

> ### 💬 인증/인가
> - **로그인**: Spring Security + JWT를 활용한 access, refresh 토큰 발급
> - **토큰 재발급**: refresh 토큰을 활용한 access 토큰 재발급
> - **유저 복구**: 관리자 권한으로 삭제된 유저 복구

> ### 💬 데일리 일기 작성
> - **일기 CRUD**: 일기 생성, 조회, 수정, 삭제 가능

## 💫 과제 내용

> - 🎯**JWT를 활용한 인증/인가 진행하기**
> - 🎯**Junit 기반에 테스트코드 작성하기**
> - 🎯**Swagger를 활용해 API 문서화하기**
> - 🎯**AWS EC2에 배포하기**


## 🛠️ 사용 기술

| 구분 | 기술 스택 |
|------|-----------|
| **Backend** | Java 17, Spring Boot 3.5.4, Spring Data JPA, Spring Security, JWT |
| **Database** | H2 |
| **Infra** | AWS EC2 |
| **Testing** | JUnit5, Swagger, Postman |
| **IDE** | IntelliJ |
| **Version Control** | GitHub |

## 🗂️ 프로젝트 구조

```
dailydiary
├── DailyDiaryApplication.class
├── common
│   ├── config
│   │   ├── SecurityConfig.class
│   │   └── SwaggerConfig.class
│   ├── entity
│   │   └── BaseEntity.class
│   ├── exception
│   │   ├── CustomException.class
│   │   ├── ErrorCode.class
│   │   ├── ErrorResponse.class
│   │   └── GlobalExceptionHandler.class
│   └── security
│       ├── CustomUserDetailsService.class
│       ├── CustomUserPrincipal.class
│       ├── JwtFilter.class
│       ├── JwtUtil.class
│       └── RefreshTokenManager.class
├── diary
│   ├── controller
│   │   └── DiaryController.class
│   ├── dto
│   │   ├── request
│   │   │   ├── CreateDiaryRequestDto.class
│   │   │   └── UpdateDiaryRequestDto.class
│   │   └── response
│   │       ├── CreateDiaryResponseDto.class
│   │       ├── DeleteDiaryResponseDto.class
│   │       ├── GetDiaryResponseDto.class
│   │       └── UpdateDiaryResponseDto.class
│   ├── entity
│   │   ├── Diary.class
│   │   └── Feeling.class
│   ├── repository
│   │   └── DiaryRepository.class
│   └── service
│       ├── DiaryService.class
│       └── DiaryServiceImpl.class
└── user
    ├── controller
    │   ├── AuthController.class
    │   └── UserController.class
    ├── dto
    │   ├── request
    │   │   ├── CreateUserRequestDto.class
    │   │   ├── LoginUserRequestDto.class
    │   │   └── UpdateUserRequestDto.class
    │   └── response
    │       ├── CreateUserResponseDto.class
    │       ├── DeleteUserResponseDto.class
    │       ├── GetProfileResponseDto.class
    │       ├── LoginUserResponseDto.class
    │       ├── LogoutUserResponseDto.class
    │       ├── ReactiveUserResponseDto.class
    │       ├── ReissueAccessTokenResponseDto.class
    │       └── UpdateUserResponseDto.class
    ├── entity
    │   ├── User.class
    │   └── UserRole.class
    ├── repository
    │   └── UserRepository.class
    └── service
        ├── AuthService.class
        ├── AuthServiceImpl.class
        ├── UserService.class
        └── UserServiceImpl.class


```

## 📝 API 문서

**Swagger 링크: http://43.201.77.96:8080/swagger-ui/index.html**

### 💫 참고사항
> **Diary API** </br>
> **GET /diary** </br>
> 스웨거에서 파라미터를 JSON 타입으로 입력받고 있기때문에 {
"page": 0,
"size": 1
} 혹은 </br> {
"page": 0,
"size": 1,
"sortBy": "createdAt",
"sortDir": "desc"
} 를 넣어주시면 정상 동작합니다.

> **Auth API** </br>
> **POST /auth/reissue** </br>
> 토큰 재발급 시 refresh 토큰으로 Authorization을 변경해주어야 합니다.

> **Auth API** </br>
> **PATCH /auth/reactive/user/{userId}** </br>
> 해당 메서드를 사용하기 위해 h2에서 유저 하나의 권한을 ADMIN으로 변경해주어야 합니다.

## 🛡️ Test Coverage
![Image](https://github.com/user-attachments/assets/5e2c99cc-bbf1-4655-b721-6e0876396714)
