package wj.baedal.market.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    /**
     * 주문 저장
     *
     * Request
     * {
     *     "userId": Long,
     *     "deliveryCity": String,
     *     "deliveryStreet": String,
     *     "deliveryZipcode": String,
     *     "orderMenuList" : [
     *              {
     *                  "menuId" : Long,
     *                  "count" : Integer
     *              },
     *              ...
     *     ]
     * }
     *
     * Response
     * Long(저장된 주문의 아이디)
     * */
    @PostMapping("/api/v1/orders")
    public Long save(@RequestBody OrderSaveRequestDto requestDto){
        return orderService.save(requestDto);
    }


    /**
     * 주문 검색(페이징 x)
     *
     * Request
     * {
     *     "userId": Long,
     *     "orderStatus": String
     * }
     *
     * Response
     * {
     *     "count": int,
     *     "data" : [
     *          {
     *              "orderId" : Long,
     *              "userId" : Long,
     *              "userName" : String,
     *              "orderDate" : LocalDateTime,
     *              "orderMenuList" : [
     *                      {
     *                          "menuId" : Long,
     *                          "count" : int
     *                      },
     *                      ...
     *              ],
     *              "city" : String,
     *              "street" : String,
     *              "zipcode" : String,
     *              "orderStatus" : String
     *          },
     *          ...
     *     ]
     * }
     * */
    @GetMapping("/api/v1/orders")
    public OrderListResponseDto searchOrderQueryDSL(@RequestBody OrderSearchCondition searchCondition){
        return orderService.searchOrderQueryDSL(searchCondition);

    }

    @GetMapping("/api/v2/orders")
    public Page<OrderResponseDto> searchOrder(@PageableDefault(sort = {"orderStatus"})Pageable pageable,
                                              @RequestBody OrderSearchCondition searchCondition){
        return orderService.searchOrder(pageable,searchCondition);
    }

    /**
     * 주문 취소 ( 주문 레코드를 삭제하는 것이 아닌 OrderStatus를 Cancel로 바꿈 )
     * */
    @DeleteMapping("/api/v1/orders/{id}")
    public Long deleteOrder(@PathVariable Long id){
        orderService.delete(id);
        return id;
    }

}
