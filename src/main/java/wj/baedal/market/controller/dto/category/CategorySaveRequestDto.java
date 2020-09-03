package wj.baedal.market.controller.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.entity.category.Category;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CategorySaveRequestDto {
    @NotNull
    private String name;
    private String parent;

    @Builder
    public CategorySaveRequestDto(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

}
