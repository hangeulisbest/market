package wj.baedal.market.controller.dto.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MenuListResponseDto<T> {
    private int count;
    private T data;

    @Builder
    public MenuListResponseDto(int count, T data) {
        this.count = count;
        this.data = data;
    }
}
