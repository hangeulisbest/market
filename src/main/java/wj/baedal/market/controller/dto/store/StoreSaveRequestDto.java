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


    @Builder
    public StoreSaveRequestDto(String name, String city, String street, String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
