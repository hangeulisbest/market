package wj.baedal.market.controller.dto.category;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryResponseDto {

    private Long id;
    private String name;
    private String parent;

    @Builder
    public CategoryResponseDto(Long id,String name, String parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }
}
