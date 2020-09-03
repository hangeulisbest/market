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

        List<OrderMenu> orderMenuList = queryFactory.selectFrom(orderMenu)
                .leftJoin(orderMenu.order, order).fetchJoin()
                .leftJoin(order.user, user).fetchJoin()
                .leftJoin(order.delivery, delivery).fetchJoin()
                .where(orderStatusEq(searchCondition.getOrderStatus()),
                        userIdEq(searchCondition.getUserId()))
                .fetch();

        Set<Order> result = orderMenuList.stream().map(o -> o.getOrder()).collect(Collectors.toSet());

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

    public BooleanExpression userIdEq(Long searchId){
        return searchId==null ? null : user.id.eq(searchId);
    }
    public BooleanExpression orderStatusEq(OrderStatus status){
        return status.equals(null) ?null: order.orderStatus.eq(status);
    }
}
