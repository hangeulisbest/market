package wj.baedal.market.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wj.baedal.market.controller.dto.user.UserListResponseDto;
import wj.baedal.market.controller.dto.user.UserResponseDto;
import wj.baedal.market.controller.dto.user.UserSaveRequestDto;
import wj.baedal.market.controller.dto.user.UserUpdateRequestDto;
import wj.baedal.market.service.userservice.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/v1/users")
    public Long save(@RequestBody UserSaveRequestDto userSaveRequestDto){
        return userService.save(userSaveRequestDto);
    }

    @GetMapping("/api/v1/users")
    public UserListResponseDto findAll(){
        return userService.findAll();
    }

    @GetMapping("/api/v1/users/{id}")
    public UserResponseDto findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PutMapping("/api/v1/users/{id}")
    public Long updateAddress(@PathVariable Long id,
                              @RequestBody UserUpdateRequestDto requestDto){

        return userService.updateAddress(id,requestDto);
    }

    @DeleteMapping("/api/v1/users/{id}")
    public Long delete(@PathVariable Long id){
        userService.delete(id);
        return id;
    }
}
