package wj.baedal.market.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

}
