package wj.baedal.market.entity.user;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.Address;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;


    protected User(String name,Address address){
        this.name = name;
        this.address = address;
    }

    public static User createUser(String name,Address address){
        User user = new User(name,address);
        return user;
    }

    public void updateAddress(String city,String street,String zipcode){
        Address address = new Address(city,street,zipcode);
        this.address = address;
    }
}
