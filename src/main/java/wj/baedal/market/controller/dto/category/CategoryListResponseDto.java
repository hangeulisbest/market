package wj.baedal.market.controller.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryListResponseDto<T> {

    private int count;
    private T data;

    @Builder
    public CategoryListResponseDto(int count, T data) {
        this.count = count;
        this.data = data;
    }
}
