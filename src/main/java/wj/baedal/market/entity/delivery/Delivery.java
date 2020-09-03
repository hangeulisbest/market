package wj.baedal.market.entity.delivery;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.Address;
import wj.baedal.market.entity.DeliveryStatus;
import wj.baedal.market.entity.order.Order;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;


    /**
     * 생성 메서드
     * */

    protected Delivery(Address address){
        this.address = address;
        this.deliveryStatus = DeliveryStatus.READY;
    }

    static public Delivery createDelivery(Address address){
        Delivery delivery = new Delivery(address);
        return delivery;
    }

    /**
     * 전용 메서드
     * */

    public void addOrder(Order order){
        this.order =order;
    }

    public void completeDelivery(){
        this.deliveryStatus = DeliveryStatus.COMPLETE;
    }


}
