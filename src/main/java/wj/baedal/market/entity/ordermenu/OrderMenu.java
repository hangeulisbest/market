package wj.baedal.market.entity.ordermenu;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.menu.Menu;
import wj.baedal.market.entity.order.Order;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu {

    @Id @GeneratedValue
    @Column(name = "orderMenu_id")
    private Long id;

    /**
     * 메뉴 주문 개수
     * */
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    protected OrderMenu(Menu menu,int count){
        this.menu = menu;
        this.count = count;
    }


    /**
     *  생성 메서드
     * */

    public static OrderMenu createOrderMenu(Menu menu,int count){
        return new OrderMenu(menu,count);
    }

    /**
     *  오더메뉴 전용 메서드
     * */

    public void addOrder(Order order){
        this.order = order;
    }



}
