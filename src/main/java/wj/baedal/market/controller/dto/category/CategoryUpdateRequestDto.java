package wj.baedal.market.controller.dto.category;

import lombok.Data;

@Data
public class CategoryUpdateRequestDto {
    // 변경하고자 하는 이름을 요청으로 보냄
    private String name;
}
