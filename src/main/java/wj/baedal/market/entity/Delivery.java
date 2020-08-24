package wj.baedal.market.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Delivery(Address address){
        this.address = address;
        this.deliveryStatus = DeliveryStatus.READY;
    }


    /**
     * 전용 메서드
     * */

    public void addOrder(Order order){
        this.order =order;
    }


}
