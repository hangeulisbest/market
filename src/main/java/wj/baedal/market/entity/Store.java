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

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    private List<CategoryStore> categoryStoreList = new ArrayList<>();

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "store" , cascade = CascadeType.ALL)
    private List<Menu> menuList = new ArrayList<>();


    public Store(Address address,String name){
        this.address = address;
        this.name = name;
    }

    public void addCategory(Category category){
        CategoryStore categoryStore = new CategoryStore(category,this);
        this.categoryStoreList.add(categoryStore);
    }

}
