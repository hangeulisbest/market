package wj.baedal.market.controller.dto.store;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.controller.dto.menu.MenuResponseDto;

import java.util.List;

@NoArgsConstructor
@Getter
public class StoreSummaryListResponseDto<T> {
    private int count;
    private T data;

    @Builder
    public StoreSummaryListResponseDto(int count, T data) {
        this.count = count;
        this.data = data;
    }
}
