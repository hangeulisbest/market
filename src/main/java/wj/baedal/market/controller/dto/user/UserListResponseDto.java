package wj.baedal.market.controller.dto.user;


import lombok.Builder;
import lombok.Getter;

@Getter
public class UserListResponseDto<T> {

    private int count;
    private T data;

    @Builder
    public UserListResponseDto(int count, T data) {
        this.count = count;
        this.data = data;
    }
}
