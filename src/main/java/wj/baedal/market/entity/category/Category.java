package wj.baedal.market.entity.category;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.categorystore.CategoryStore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Table(uniqueConstraints = {
        @UniqueConstraint(name = "CATEGORY_NAME_UNIQUE",columnNames = {"NAME"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
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
    private List<CategoryStore> categoryStoreList= new ArrayList<>();


    protected Category(String name){
        this.name = name;
    }

    /**
     * 생성 메서드
     *
     */
    public static Category createCategory(String name){
        Category category = new Category(name);
        return category;
    }


    /**
     *  양방향 연관관계 매핑
     *
     */

    public void setParent(Category parent){
        this.parent = parent;
        parent.child.add(this);
    }

    /**
     * 전용 메서드
     *
     * */

    public void changeName(String name){
        this.name=name;
    }

}
