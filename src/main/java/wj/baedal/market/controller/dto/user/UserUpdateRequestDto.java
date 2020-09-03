package wj.baedal.market.controller.dto.user;


import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private String city;
    private String street;
    private String zipcode;

    @Builder
    public UserUpdateRequestDto(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
