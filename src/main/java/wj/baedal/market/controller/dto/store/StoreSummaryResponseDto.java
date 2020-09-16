package wj.baedal.market.controller.dto.store;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StoreSummaryResponseDto {
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @Builder
    @QueryProjection
    public StoreSummaryResponseDto(Long id, String name, String city, String street, String zipcode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
