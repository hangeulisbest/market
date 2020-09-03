package wj.baedal.market.controller.dto.store;


import lombok.Builder;
import lombok.Data;

@Data
public class StoreSearchListResponseDto<T> {
    private int count;
    private T data;

    @Builder
    public StoreSearchListResponseDto(int count, T data) {
        this.count = count;
        this.data = data;
    }
}
