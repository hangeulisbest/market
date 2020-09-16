## 도메인 설계도
<img width="1010" alt="스크린샷 2020-09-07 오후 1 35 01" src="https://user-images.githubusercontent.com/48645552/92348753-f629eb80-f10e-11ea-9f9b-80740a143c44.png">


## API Controller 주석 설명

**예시 (controller/OrderApiController.java)**    
<img width="835" alt="스크린샷 2020-09-07 오후 1 37 32" src="https://user-images.githubusercontent.com/48645552/92348850-4b65fd00-f10f-11ea-8b45-ed7776f9f0b1.png">

모든 API Controller 에는 REQUEST 와 RESPONSE 에 관한 정보를 미리 보여주는 주석을 제공합니다.  

1. Request 는 ReqeustBody 로 전달되는 JSON 형태 포멧입니다.
2. Response 는 해당 API를 호출한 RESPONSE 입니다.


# Category API 테이블  

|METHOD|URL|DESCRIPTION|
|:---:|:-------:|:-------------:|
|POST|/api/v1/categories|카테고리 저장|
|GET|/api/v1/categories|모든 카테고리 조회|
|PUT|/api/v1/categories/{id}|카테고리의 이름 수정|
|DELETE|/api/v1/categories/{id}|카테고리 삭제(만약 참조하는 가게가 있다면 삭제안됨)|


# Order API 테이블

|METHOD|URL|DESCRIPTION|
|:---:|:-------:|:-------------:|
|POST|/api/v1/orders|주문 저장|
|GET|/api/v1/orders|주문 검색|
|DELETE|/api/v1/orders/{id}|주문 취소|

# Store API 테이블

|METHOD|URL|DESCRIPTION|
|:---:|:-------:|:-------------:|
|POST|/api/v1/stores|가게 저장|
|PUT|/api/v1/stores/{id}|가게 정보 수정|
|GET|/api/v1/stores/{id}|가게 한개 정보 조회|
|GET|/api/v3/stores|모든 가게 정보 조회|

# User API 테이블

|METHOD|URL|DESCRIPTION|
|:---:|:-------:|:-------------:|
|POST|/api/v1/users|유저 저장|
|GET|/api/v1/users|모든 유저 조회|
|GET|/api/v1/users/{id}|1명의 유저 조회|
|PUT|/api/v1/users/{id}|유저 정보 수정|
|DELETE|/api/v1/users/{id}|유저 삭제|
