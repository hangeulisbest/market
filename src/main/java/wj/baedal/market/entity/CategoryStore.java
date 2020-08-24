package wj.baedal.market.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CategoryStore {

    @Id @GeneratedValue
    @Column(name = "categorystore_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public CategoryStore(Category category,Store store){
        this.category = category;
        this.store =store;
    }
}
