## 도메인 설계도
<img width="1010" alt="스크린샷 2020-09-07 오후 1 35 01" src="https://user-images.githubusercontent.com/48645552/92348753-f629eb80-f10e-11ea-9f9b-80740a143c44.png">


## 디렉토리로 보는 도메인 구조
entity/    
    category/    
        Category.java    
        CategoryRepository.java    
    categoryStore/    
        CategoryStore.java    
        CategoryStroeRepository.java    
    delivery/    
        Delivery.java    
    menu/    
        Menu.java    
        MenuRepository.java    
    order/    
        Order.java    
        OrderRepository.java    
    ordermenu/    
        OrderMenu.java    
        OrderMenuRepository.java    
    store/     
        Store.java  
        StoreRepository.java  
    user/  
        User.java   
        UserRepository.java  
    Address.java  
    DeliveryStatus.java   
    OrderStatus.java   

## Controller 코드 설명

<img width="835" alt="스크린샷 2020-09-07 오후 1 37 32" src="https://user-images.githubusercontent.com/48645552/92348850-4b65fd00-f10f-11ea-8b45-ed7776f9f0b1.png">

Controller 에서 
Request 는 ReqeustBody 로 전달되는 JSON 형태 포멧입니다.
Response 는 해당 API를 호출한 RESPONSE 입니다.

