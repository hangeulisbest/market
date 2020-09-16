package wj.baedal.market.repository.user;

import lombok.Data;

/**
 * 유저의 이름과 도시로 유저를 검색
 * */

@Data
public class UserSearchCondition {
    private String name;
    private String city;
}
