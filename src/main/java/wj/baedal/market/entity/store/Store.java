package wj.baedal.market.entity.store;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.Address;
import wj.baedal.market.entity.categorystore.CategoryStore;
import wj.baedal.market.entity.menu.Menu;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Table(uniqueConstraints = {
        @UniqueConstraint(name = "STORE_NAME_UNIQUE",columnNames = {"NAME"})
})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    private List<CategoryStore> categoryStoreList = new ArrayList<>();

    @OneToMany(mappedBy = "store" , cascade = CascadeType.ALL)
    private List<Menu> menuList = new ArrayList<>();


    protected Store(Address address,String name){
        this.address = address;
        this.name = name;
    }

    // Store 생성 메서드

    public static Store createStore(Address address,String name){
        Store store = new Store(address,name);
        return store;
    }


//
//    public void addMenu(String name,int price){
//        Menu menu = Menu.createMenu(name,price,this);
//    }
//
//    public void addMenuList(List<Menu> menuList){
//        this.menuList.clear();
//        for(int i=0;i<menuList.size();i++){
//            Menu menu = Menu.createMenu(menuList.get(i).getName(),menuList.get(i).getPrice(),this);
//        }
//    }

}
