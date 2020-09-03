package wj.baedal.market.entity.categorystore;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.store.Store;
import wj.baedal.market.entity.category.Category;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    protected CategoryStore(Category category, Store store){
        this.category = category;
        this.store =store;
    }

    /**
     *
     * 생성 메서드
     *
     * 카테고리와 가게를 연결하는 작업
     * 양방향 연관관계 메서드 추가
     *
    */
    public static CategoryStore createCategoryStore(Category category,Store store){
        CategoryStore categoryStore = new CategoryStore(category,store);
        store.getCategoryStoreList().add(categoryStore);
        category.getCategoryStoreList().add(categoryStore);
        if(category.getParent()!=null) {
            CategoryStore.createCategoryStore(category.getParent(),store);
        }
        return categoryStore;
    }
}
