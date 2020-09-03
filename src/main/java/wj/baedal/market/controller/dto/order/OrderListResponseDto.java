package wj.baedal.market.controller.dto.order;


import lombok.Builder;
import lombok.Data;

@Data
public class OrderListResponseDto <T> {
    private int count;
    private T data;

    @Builder
    public OrderListResponseDto(int count, T data) {
        this.count = count;
        this.data = data;
    }
}
