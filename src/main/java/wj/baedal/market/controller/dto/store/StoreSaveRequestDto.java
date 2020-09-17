package wj.baedal.market.controller.dto.store;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class StoreSaveRequestDto {

    private String name;
    // address
    private String city;
    private String street;
    private String zipcode;

    private String category;


    @Builder
    public StoreSaveRequestDto(String name, String city, String street, String zipcode,String category) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.category = category;
    }
}
