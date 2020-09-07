## 도메인 설계도
<img width="1010" alt="스크린샷 2020-09-07 오후 1 35 01" src="https://user-images.githubusercontent.com/48645552/92348753-f629eb80-f10e-11ea-9f9b-80740a143c44.png">


## 디렉토리로 보는 도메인 구조
entity/    
&nbsp;&nbsp;&nbsp;&nbsp;category/    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      Category.java    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        CategoryRepository.java    
&nbsp;&nbsp;&nbsp;&nbsp;    categoryStore/    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        CategoryStore.java    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        CategoryStroeRepository.java    
&nbsp;&nbsp;&nbsp;&nbsp;    delivery/    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        Delivery.java    
&nbsp;&nbsp;&nbsp;&nbsp;   menu/    
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       Menu.java    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        MenuRepository.java    
&nbsp;&nbsp;&nbsp;&nbsp;    order/    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        Order.java    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        OrderRepository.java    
&nbsp;&nbsp;&nbsp;&nbsp;    ordermenu/    
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       OrderMenu.java    
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       OrderMenuRepository.java    
 &nbsp;&nbsp;&nbsp;&nbsp;   store/     
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      Store.java  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     StoreRepository.java  
   &nbsp;&nbsp;&nbsp;&nbsp; user/  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     User.java   
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      UserRepository.java  
 &nbsp;&nbsp;&nbsp;&nbsp;   Address.java  
 &nbsp;&nbsp;&nbsp;&nbsp;   DeliveryStatus.java   
 &nbsp;&nbsp;&nbsp;&nbsp;   OrderStatus.java   

## Controller 코드 설명

<img width="835" alt="스크린샷 2020-09-07 오후 1 37 32" src="https://user-images.githubusercontent.com/48645552/92348850-4b65fd00-f10f-11ea-8b45-ed7776f9f0b1.png">

Controller 에서 
Request 는 ReqeustBody 로 전달되는 JSON 형태 포멧입니다.
Response 는 해당 API를 호출한 RESPONSE 입니다.

