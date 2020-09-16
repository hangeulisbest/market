package wj.baedal.market.controller.dto.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuRequestDto {
    private String name;
    private int price;

    @Builder
    public MenuRequestDto(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
