package wj.baedal.market.controller.dto.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;
    private String name;
    private int price;

    @Builder
    public MenuResponseDto(Long id,String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
