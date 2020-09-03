package wj.baedal.market.controller.dto.user;


import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public UserResponseDto(Long id,String name, String city, String street, String zipcode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
