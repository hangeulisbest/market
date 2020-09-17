## 동영상 보러 가기
|링크|
|:---------------------------:|
|https://onejunu.tistory.com/77|

## 데이터베이스 모델링

<img width="976" alt="스크린샷 2020-09-18 오전 12 14 49" src="https://user-images.githubusercontent.com/48645552/93490936-11350080-f944-11ea-9c93-4d7e3c2e1b1a.png">

## 비즈니스 요구 사항 간략 정리

|비즈니스 요구 사항|
|:---|
|1. 하나의 회원은 여러 개의 주문을 할 수 있습니다.|
|2. 하나의 주문은 하나의 배달정보를 가지고 있어야 합니다.|
|3. 배달이 완료된 주문은 취소할 수 없습니다.|
|4. 하나의 주문은 여러 개의 메뉴와 각각 몇개 주문했는지 가지고 있어야 합니다.|
|5. 하나의 카테고리는 하나의 부모 카테고리를 가질 수 있습니다.|
|6. 하나의 카테고리는 여러개의 자식 카테고리를 가질 수 있습니다.|
|7. 하나의 가게는 여러개의 카테고리를 지닐 수 있습니다.|
|8. 메뉴는 가게에 종속적입니다. 즉, 가게가 사라지면 메뉴들은 사라집니다.|
|9. 가게가 참조하고 있는 카테고리는 삭제할 수 없습니다.|
|10. 가게의 이름은 유일합니다.|


## 프로젝트 특징

|프로젝트의 특징|
|:---|
| Mustache 를 이용하여 화면을 렌더링 하였습니다.|
| API controller와 화면용 Controller를 분리하였습니다.|
| 화면 조회용 Repository는 따로 만들어 queryDSL을 사용하여 성능을 튜닝하였습니다.|


## 프로젝트 화면 이미지 캡쳐
### 메인
<img width="1433" alt="스크린샷 2020-09-18 오전 12 35 22" src="https://user-images.githubusercontent.com/48645552/93493413-d84a5b00-f946-11ea-80e3-933fc2d6ec60.png">

### 회원등등록
<img width="537" alt="스크린샷 2020-09-18 오전 12 35 41" src="https://user-images.githubusercontent.com/48645552/93493456-e39d8680-f946-11ea-83d0-39d031dd2d7c.png">

### 회원검색
<img width="581" alt="스크린샷 2020-09-18 오전 12 36 17" src="https://user-images.githubusercontent.com/48645552/93493542-f9ab4700-f946-11ea-85a2-e35f92034218.png">

### 회원수정
<img width="581" alt="스크린샷 2020-09-18 오전 12 36 44" src="https://user-images.githubusercontent.com/48645552/93493595-07f96300-f947-11ea-9209-511a3e5e7146.png">

### 가게등록
<img width="581" alt="스크린샷 2020-09-18 오전 12 37 01" src="https://user-images.githubusercontent.com/48645552/93493623-12b3f800-f947-11ea-9f72-6af2c14aa615.png">

### 가게 검색
<img width="581" alt="스크린샷 2020-09-18 오전 12 37 22" src="https://user-images.githubusercontent.com/48645552/93493656-1f385080-f947-11ea-84c7-35d28409da37.png">

### 가게 수정
<img width="527" alt="스크린샷 2020-09-18 오전 12 38 01" src="https://user-images.githubusercontent.com/48645552/93493740-370fd480-f947-11ea-9de1-fc7c94ac66d9.png">
<img width="527" alt="스크린샷 2020-09-18 오전 12 38 15" src="https://user-images.githubusercontent.com/48645552/93493779-3ecf7900-f947-11ea-9cd6-701c60afcc69.png">

### 주문 검색
<img width="537" alt="스크린샷 2020-09-18 오전 12 39 20" src="https://user-images.githubusercontent.com/48645552/93493936-66bedc80-f947-11ea-8448-950edc633e6b.png">

### 주문 하기
<img width="527" alt="스크린샷 2020-09-18 오전 12 38 48" src="https://user-images.githubusercontent.com/48645552/93493866-53137600-f947-11ea-9ae8-063e19af8428.png">


### 주문 조회
<img width="537" alt="스크린샷 2020-09-18 오전 12 39 45" src="https://user-images.githubusercontent.com/48645552/93493979-763e2580-f947-11ea-933a-b3f5c2271ab8.png">




