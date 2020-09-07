package wj.baedal.market.service.orderservice;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wj.baedal.market.controller.dto.order.OrderListResponseDto;
import wj.baedal.market.controller.dto.order.OrderResponseDto;
import wj.baedal.market.controller.dto.order.OrderSaveRequestDto;
import wj.baedal.market.entity.Address;
import wj.baedal.market.entity.delivery.Delivery;
import wj.baedal.market.entity.menu.Menu;
import wj.baedal.market.entity.menu.MenuRepository;
import wj.baedal.market.entity.order.Order;
import wj.baedal.market.entity.order.OrderRepository;
import wj.baedal.market.entity.ordermenu.OrderMenu;
import wj.baedal.market.entity.user.User;
import wj.baedal.market.entity.user.UserRepository;
import wj.baedal.market.repository.order.OrderSearchCondition;
import wj.baedal.market.repository.order.OrderSearchRepository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrderSearchRepository orderSearchRepository;

    /** 주문 저장 */
    @Transactional
    public Long save(OrderSaveRequestDto requestDto){
        /** 주문자의 정보가 존재하는지 검사*/
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                ()->new IllegalArgumentException("해당 유저가 없습니다.")
        );

        /**
         *  모든 메뉴를 가져온 다음,
         *  메뉴의 아이디로 바로바로 메뉴를 찾을 수 있도록 HashMap을 생성하는 작업
         *
         * */
        HashMap<Long,Menu> menuHashMap = new HashMap<>();
        List<Menu> menuList = menuRepository.findAll();
        menuList.stream().forEach(o->
            menuHashMap.put(o.getId(),o)
        );

        /** request에서 요구하는 MenuId가 hasMap에 없다면 존재하지 않는 메뉴이기 때문에 에러 반환*/
        requestDto.getOrderMenuList().stream().forEach(
                o->{
                    if(!menuHashMap.containsKey(o.getMenuId())){
                        throw new IllegalArgumentException("해당 메뉴가 존재하지 않습니다.");
                    }
                }
        );

        /** 배달하는 장소를 저장하기 위한 Delivery 객체 생성*/
        Delivery delivery = Delivery.createDelivery(
                new Address(requestDto.getDeliveryCity(),
                        requestDto.getDeliveryStreet(),
                        requestDto.getDeliveryZipcode())
        );

        /** 모든 메뉴가 존재하는 것을 확인했으므로
         *  어떤 메뉴를 몇개 주문했는지에 관한 리스트를 생성
         * */
        List<OrderMenu> orderMenuCollect = requestDto.getOrderMenuList().stream().map(
                o -> OrderMenu.createOrderMenu(menuHashMap.get(o.getMenuId()), o.getCount())
        ).collect(Collectors.toList());

        /** Order객체에 주문자 정보와 배달주소 그리고 어떤메뉴를 몇개 주문했는지 리스트를 넘겨주면서
         *  객체를 생성하고 영속성 컨텍스트에 저장*/
        Order order = Order.createOrder(user, delivery, orderMenuCollect);
        orderRepository.save(order);

        return order.getId();
    }


    /**주문 검색*/
    @Transactional(readOnly = true)
    public OrderListResponseDto searchOrderQueryDSL(OrderSearchCondition searchCondition){
        /**
         * 검색용 Repository 는 따로 만들었음.
         * repository.order.OrderSearchRepository 참
         * */
        List<OrderResponseDto> result = orderSearchRepository.searchOrderQueryDSL(searchCondition);

        /**
         * List<OrderResponseDto> -> OrderListResponseDto
         * */
        return OrderListResponseDto.builder()
                .data(result)
                .count(result.size())
                .build();
    }

    /**주문 취소*/
    @Transactional
    public void delete(Long id){

        /** 해당하는 아이디에 주문객체가 존재하는지 검사*/
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 주문이 없습니다.")
        );

        /** 주문 객체 자체의 메소드로 orderStatus 를 CANCEL로 변경 */
        order.cancel();

    }


}
