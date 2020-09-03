package wj.baedal.market.controller.dto.store;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StoreListResponseDto<T> {
    private int count;
    private T data;

    @Builder
    public StoreListResponseDto(int count, T data) {
        this.count = count;
        this.data = data;
    }
}
