package wj.baedal.market.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    private String name;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;


    public Menu(String name,int price,Store store){
        this.name = name;
        this.price = price;
        this.store = store;
        store.getMenuList().add(this);
    }
}
