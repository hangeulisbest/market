package wj.baedal.market.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wj.baedal.market.controller.dto.order.OrderListResponseDto;
import wj.baedal.market.controller.dto.order.OrderResponseDto;
import wj.baedal.market.controller.dto.order.OrderSaveRequestDto;
import wj.baedal.market.repository.order.OrderSearchCondition;
import wj.baedal.market.service.orderservice.OrderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    public Long save(@RequestBody OrderSaveRequestDto requestDto){
        return orderService.save(requestDto);
    }


    @GetMapping("/api/v1/orders")
    public OrderListResponseDto searchOrderQueryDSL(@RequestBody OrderSearchCondition searchCondition){
        List<OrderResponseDto> orderResponseDtos = orderService.searchOrderQueryDSL(searchCondition);
        return OrderListResponseDto.builder()
                .data(orderResponseDtos)
                .count(orderResponseDtos.size())
                .build();
    }
    @DeleteMapping("/api/v1/orders/{id}")
    public Long deleteOrder(@PathVariable Long id){
        orderService.delete(id);
        return id;
    }

}
