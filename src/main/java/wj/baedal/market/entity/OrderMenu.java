package wj.baedal.market.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.boot.model.naming.ImplicitNameSource;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu {

    @Id @GeneratedValue
    @Column(name = "orderMenu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public OrderMenu(Order order, Menu menu){
        this.order = order;
        this.menu = menu;
    }


}
