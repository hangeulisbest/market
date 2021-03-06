package wj.baedal.market.entity.menu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.store.Store;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    }

    /**
     * 메뉴 생성 메서드 (store에 의존적임)
     * */

    public static Menu createMenu(String name,int price,Store store){
        Menu menu = new Menu(name,price,store);
        store.getMenuList().add(menu);
        return menu;
    }
}
