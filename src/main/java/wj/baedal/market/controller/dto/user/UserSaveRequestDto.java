package wj.baedal.market.controller.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.Address;
import wj.baedal.market.entity.user.User;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String name;
    private String city;
    private String street;
    private String zipcode;


    @Builder
    public UserSaveRequestDto(String name, String city, String street, String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public User toEntity(){
        return User.createUser(
                name,
                new Address(city,street,zipcode)
        );
    }
}
