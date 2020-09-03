package wj.baedal.market.service.orderservice;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wj.baedal.market.controller.dto.order.OrderResponseDto;
import wj.baedal.market.controller.dto.order.OrderSaveRequestDto;
import wj.baedal.market.entity.Address;
import wj.baedal.market.entity.delivery.Delivery;
import wj.baedal.market.entity.menu.Menu;
import wj.baedal.market.entity.menu.MenuRepository;
import wj.baedal.market.entity.order.Order;
import wj.baedal.market.entity.order.OrderRepository;
import wj.baedal.market.entity.ordermenu.OrderMenu;
import wj.baedal.market.entity.store.Store;
import wj.baedal.market.entity.store.StoreRepository;
import wj.baedal.market.entity.user.User;
import wj.baedal.market.entity.user.UserRepository;
import wj.baedal.market.repository.order.OrderSearchCondition;
import wj.baedal.market.repository.order.OrderSearchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrderSearchRepository orderSearchRepository;

    @Transactional
    public Long save(OrderSaveRequestDto requestDto){
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                ()->new IllegalArgumentException("해당 유저가 없습니다.")
        );

        requestDto.getOrderMenuList().stream().forEach(
                o->{
                    menuRepository.findById(o.getMenuId()).orElseThrow(
                            ()->new IllegalArgumentException("해당 메뉴가 없습니다.")
                    );
                }
        );

        Delivery delivery = Delivery.createDelivery(
                new Address(requestDto.getDeliveryCity(),
                        requestDto.getDeliveryStreet(),
                        requestDto.getDeliveryZipcode())
        );

        List<OrderMenu> orderMenuCollect = requestDto.getOrderMenuList().stream().map(
                o -> OrderMenu.createOrderMenu(menuRepository.findById(o.getMenuId()).get(), o.getCount())
        ).collect(Collectors.toList());


        Order order = Order.createOrder(user, delivery, orderMenuCollect);

        orderRepository.save(order);

        return order.getId();
    }


    @Transactional(readOnly = true)
    public List<OrderResponseDto> searchOrderQueryDSL(OrderSearchCondition searchCondition){
        return orderSearchRepository.searchOrderQueryDSL(searchCondition);
    }

    @Transactional
    public void delete(Long id){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 주문이 없습니다.")
        );

        order.cancel();

    }


}
