package wj.baedal.market.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import wj.baedal.market.controller.dto.order.OrderResponseDto;
import wj.baedal.market.controller.dto.ordermenu.OrderMenuResponseDto;
import wj.baedal.market.entity.OrderStatus;
import wj.baedal.market.entity.delivery.QDelivery;
import wj.baedal.market.entity.order.Order;
import wj.baedal.market.entity.order.QOrder;
import wj.baedal.market.entity.ordermenu.OrderMenu;
import wj.baedal.market.entity.ordermenu.QOrderMenu;
import wj.baedal.market.entity.user.QUser;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static wj.baedal.market.entity.delivery.QDelivery.delivery;
import static wj.baedal.market.entity.order.QOrder.order;
import static wj.baedal.market.entity.ordermenu.QOrderMenu.orderMenu;
import static wj.baedal.market.entity.user.QUser.user;

@Repository
public class OrderSearchRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public OrderSearchRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<OrderResponseDto> searchOrderQueryDSL(OrderSearchCondition searchCondition){

        /**
         *
         * OrderMenu를 기준으로 order는 다대일 관계이며,
         * order 기준으로 user또한 다대일 관계이며,
         * order 와 delivery는 일대일 관계이다.
         * 따라서 orderMenu를 기준으로 join fetch를 이용해 모든 객체를 한번에 다 가져오면 쿼리 1번에 가져올 수 있음.
         *
         * where 절은 주문상태와 유저의 아이디를 검사하는 항목임.
         *
         * */

        List<OrderMenu> orderMenuList = queryFactory.selectFrom(orderMenu)
                .leftJoin(orderMenu.order, order).fetchJoin()
                .leftJoin(order.user, user).fetchJoin()
                .leftJoin(order.delivery, delivery).fetchJoin()
                .where(orderStatusEq(searchCondition.getOrderStatus()),
                        userIdEq(searchCondition.getUserId()))
                .fetch();


        /**
         *
         * OrderMenu 를 Order의 Set으로 바꾸는 작업.
         *
         * ex) 1개의 주문에는 여러개의 메뉴가 들어갈 수 있다. 따라서 OrderMenu중에 중복된 Order가 있을 수 있음!
         * 따라서 OrderMenu를 스트림을 형성한후에 Order만 가져와 Set에 넣으면 중복된 Order를 거를 수 있다.
         *
         * */
        Set<Order> result = orderMenuList.stream().map(o -> o.getOrder()).collect(Collectors.toSet());


        /**
         *
         * Set<Order> -> List<OrderResponseDto> 로 바꾸는 작업
         * 모든 객체를 이미 orderMenuList를 가져오면서 가져왔기 때문에 추가적으로 데이터베이스에 쿼리를 날리지 않음
         * */
        return result.stream().map(
                o->OrderResponseDto.builder()
                        .orderId(o.getId())
                    .userId(o.getUser().getId())
                    .username(o.getUser().getName())
                    .orderDate(o.getOrderDate())
                    .city(o.getDelivery().getAddress().getCity())
                    .street(o.getDelivery().getAddress().getStreet())
                    .zipcode(o.getDelivery().getAddress().getZipcode())
                    .orderStatus(o.getOrderStatus())
                    .orderMenuList(
                            /**
                             * 각각의 주문에 대해서 주문 하나당 가지고 있는 주문 메뉴리스트를 추가하는 과정
                             * 주문 메뉴 리스트는 무엇을 주문했는 지와 몇개를 주문했는지에 관한 정보가 들어간다
                             * */
                            orderMenuList.stream().filter(x->x.getOrder().getId()==o.getId())
                            .map(
                                    x-> OrderMenuResponseDto.builder()
                                    .count(x.getCount())
                                    .menuId(x.getMenu().getId())
                                    .build()
                            ).collect(Collectors.toList())
                    )
                    .build()
        ).collect(Collectors.toList());

    }

    /** 유저의 아이디가 null 이라면 null 을 반환하며 아니면 같은지 다른지 boolean으로 반환*/
    public BooleanExpression userIdEq(Long searchId){
        return searchId==null ? null : user.id.eq(searchId);
    }

    /** 주문상테가 null 이라면 null 을 반환하며 아니면 같은지 다른지 boolean으로 반환*/
    public BooleanExpression orderStatusEq(OrderStatus status){
        return status.equals(null) ?null: order.orderStatus.eq(status);
    }
}
