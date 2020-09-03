package wj.baedal.market.controller.dto.store;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StoreResponseDto<T,U> {

    private String name;

    private String city;
    private String street;
    private String zipcode;

    private U categoryList;
    private T menuList;

    @Builder
    public StoreResponseDto(String name, String city, String street, String zipcode, U categoryList, T menuList) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.categoryList = categoryList;
        this.menuList = menuList;
    }
}
