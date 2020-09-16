package wj.baedal.market.repository.user;


import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import wj.baedal.market.controller.dto.user.UserResponseDto;
import wj.baedal.market.entity.user.QUser;
import wj.baedal.market.entity.user.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static wj.baedal.market.entity.user.QUser.user;

@Repository
public class UserSearchRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserSearchRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     *  유저 검색
     *
     * */
    public Page<UserResponseDto> searchUser(Pageable pageable, UserSearchCondition condition){
        /**검색 조건에 해당하는 유저를 가져옵니다*/
        QueryResults<User> results = queryFactory.selectFrom(user)
                .where(userNameEq(condition.getName()),
                        cityNameEq(condition.getCity()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        /**Dto로 변환합니다. 이때 페이지의 내용을 모두 변환합니다.*/
        List<UserResponseDto> content = results.getResults().stream().map(
                o -> UserResponseDto.builder().id(o.getId())
                        .city(o.getAddress().getCity())
                        .street(o.getAddress().getStreet())
                        .zipcode(o.getAddress().getZipcode())
                        .name(o.getName())
                        .build()
        ).collect(Collectors.toList());

        /** count 쿼리를 날립니다 */
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }

    public BooleanExpression userNameEq(String name){
        return StringUtils.hasText(name)? user.name.eq(name) : null;
    }
    public BooleanExpression cityNameEq(String name){
        return StringUtils.hasText(name)? user.address.city.eq(name) : null;
    }
}
