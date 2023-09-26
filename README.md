# myblog
Spring과 Java를 사용한 개인 블로그 서비스
## 개발 환경
- IntelliJ
- Postman
- GitHub
- Mysql Workbench
- Visual Studio Code

## 사용 기술
### 백엔드
#### 주요 프레임워크 / 라이브러리
- Java 17 openjdk
- SpringBoot 2.7.15
- SpringBoot Security
- Spring Data JPA

#### Build tool
- Gradle

#### Database
- Mysql

### 프론트엔드
- Javascript
- Html/Css
- Thymeleaf
- Bootstrap 5

### 기타 주요 라이브러리
- Lombok

## DB설계

- 필수속성들만 포함하여 심플하게 설계하였으며, 모든 테이블의 공통 메타데이터를 별도의 Entity로 분리

![image](https://github.com/Choi-SeungMi/myblog/assets/115157482/7bd033ec-ed19-4923-82aa-0aa14033be7d)

## RESTful API설계

- HTTP URI는 Resource만을 명시하였으며, HTTP Method를 통해 해당 자원에 대한 CRUD Operation을 적용하여 RESTful하게 설계

![image](https://github.com/Choi-SeungMi/myblog/assets/115157482/cbb52493-02e2-4434-b8b6-f6396aad4dde)


## 주요기능

### 메인 페이지 최신 게시글 조회

- 메인페이지에서 최근에 작성된 게시글 5개를 sub-title과 함께 표시

![1](https://github.com/Choi-SeungMi/myblog/assets/115157482/d996c768-b599-4734-8eeb-eea48f70dbf8)




### 카테고리별 게시글 조회

- 카테고리 이름 옆에는 해당 카테고리에 연관된 게시글의 수를 표시하였으며, 카테고리를 누르면 해당 카테고리의 게시글 목록으로 이동 

![2](https://github.com/Choi-SeungMi/myblog/assets/115157482/1263f0ea-5d20-4336-88ee-fd80c9c35137)




### 카테고리 편집 (로그인 된 사용자만 가능)

- '전체글' 카테고리는 default 카테고리이며 삭제 및 수정이 불가
- '전체글' 외의 카테고리는 이름 및 위치 수정, 삭제 가능
- 카테고리를 삭제하면 해당 카테고리에 포함되는 게시글도 함께 삭제
- 새로운 카테고리 추가 가능

![3](https://github.com/Choi-SeungMi/myblog/assets/115157482/a0c8f111-fcf7-4af7-90d2-efeded8898f0)

![4](https://github.com/Choi-SeungMi/myblog/assets/115157482/274da656-dca9-4b2a-946a-6cce0ff779bc)

![5](https://github.com/Choi-SeungMi/myblog/assets/115157482/09dba6d7-5804-4b42-8410-fa4fb3a00a9e)




### 게시글 작성 (로그인 된 사용자만 가능)

- 게시글 작성 버튼을 누르기 전 머물렀던 카테고리의 이름이 자동으로 선택
- 자동으로 선택된 카테고리를 원하는 카테고리로 수정 가능
- Summernote Editor를 적용하여 다양한 기능 사용 가능
- 이미지의 인코딩 타입인 Base64는 매우 긴 길이 때문에 DB에 문제가 발생할 수 있으므로 별도의 저장소에 저장 후 저장경로 링크를 통해 파일에 접근

![게시글 작성](https://github.com/Choi-SeungMi/myblog/assets/115157482/1dd1faa9-b148-4adb-8ceb-d5ddbce8e33a)




### 게시글 수정 (로그인 된 사용자만 가능)

- 기존에 작성된 내용이 자동으로 불러와지고 수정 후 저장하면 수정된 내용이 DB에 반영

![게시글 수정](https://github.com/Choi-SeungMi/myblog/assets/115157482/cd90ee08-e5fc-4985-af75-69bd8aa65df4)




### 게시글 삭제 (로그인 된 사용자만 가능)

- '정말로 삭제하시겠습니까?' 라는 Confirm 후 true일 때 DB에서 삭제

![게시글 삭제](https://github.com/Choi-SeungMi/myblog/assets/115157482/1a6ff046-ac74-448a-afaf-b99f41a5f8ab)






  
