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
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<Category> child = new ArrayList<>();


    @OneToMany(mappedBy = "category")
    private List<CategoryStore> categoryStore= new ArrayList<>();

    public Category(String name){
        this.name = name;
    }

    public void setParent(Category parent){
        this.parent = parent;
        parent.child.add(this);
    }

}
