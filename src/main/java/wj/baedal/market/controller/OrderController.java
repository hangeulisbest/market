package wj.baedal.market.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wj.baedal.market.controller.dto.menu.MenuListResponseDto;
import wj.baedal.market.controller.dto.menu.MenuResponseDto;
import wj.baedal.market.controller.dto.order.OrderResponseDto;
import wj.baedal.market.controller.dto.user.UserListResponseDto;
import wj.baedal.market.controller.dto.user.UserResponseDto;
import wj.baedal.market.entity.menu.Menu;
import wj.baedal.market.entity.menu.MenuRepository;
import wj.baedal.market.entity.order.Order;
import wj.baedal.market.entity.order.OrderRepository;
import wj.baedal.market.entity.user.UserRepository;
import wj.baedal.market.service.orderservice.OrderService;
import wj.baedal.market.service.userservice.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("local")
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @GetMapping("/order/save")
    public String orderSave(Model model){
        /**
         *  주문할 메뉴를 볼수 있도록 제공
         * */
        List<MenuResponseDto> menuList = menuRepository.findAll().stream().map(o -> MenuResponseDto
                .builder()
                .id(o.getId())
                .price(o.getPrice())
                .name(o.getName())
                .build()
        ).collect(Collectors.toList());


        model.addAttribute("menuList",menuList);
        return "order/order-save";
    }

    @GetMapping("/order/search")
    public String orderSearch(){
        return "order/order-search";
    }


    @GetMapping("/order/update/{id}")
    public String orderUpdate(@PathVariable Long id , Model model){
        OrderResponseDto order = orderService.findById(id);
        model.addAttribute("order",order);
        return "order/order-update";
    }


}
