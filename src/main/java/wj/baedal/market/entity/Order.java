package wj.baedal.market.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenuList=new ArrayList<>();



    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;



    /**
     *
     * 생성 메서드
     * Delivery와 함께 생성
     *
     * */

    public static Order createOrder(User user,Delivery delivery,OrderMenu... orderMenuList){
            Order order = new Order();
            order.user = user;
            order.delivery = delivery;
            for(OrderMenu orderMenu : orderMenuList){
                order.getOrderMenuList().add(orderMenu);
                orderMenu.addOrder(order);
            }
            order.orderStatus= OrderStatus.ORDER;
            order.orderDate = LocalDateTime.now();
            return order;
    }

}
