# 배다른민족 (배달의 민족 클론 웹 어플리케이션)

2020.08.24 시작

2020.08.24 작업

1. categoryStore의 리스트형 변수이름이 categoryStore 되어있는 것을 categoryStoreList로 변경.
2. category를 public 생성자로 생성하던 것을 static 메서드로 생성하도록 변경.
3. CategoryStore의 기본생성자를 protected로 변경.
4. category와 store를 연결해주는 static createCategoryStore 메서드를 추가. 이때 store와 category에 양방향 매핑추가.
5. Delivery 기본 생성자 추가. 
6. Delivery 에서 Order를 참조할 수 있도록 addOrder 추가.
7. Menu 의 기본생성자 protected 변경.
8. Menu를 생성하는 static createMenu 메서드 추가.
9. Order 가 영속성 컨텍스트에 올라온다면 OrderMenuList도 올라가도록 cascade.ALL 설정.
10. Order를 생성하는 static createOrder 메서드 추가.
11. OrderMenu에 주문의 개수를 기록하는 int 형 count 필드 추가.
12. Store의 기본생성자 protected 변경. static createStore 메서드 추가.
13. User의 기본생성자 protected 변경.  static createUser 메서드 추가.
14. OrderRepository 추가.
15. CategoryStoreRepository 추가.


