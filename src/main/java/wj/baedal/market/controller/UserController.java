package wj.baedal.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wj.baedal.market.controller.dto.user.UserResponseDto;
import wj.baedal.market.service.userservice.UserService;

@Profile("local")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/save")
    public String userSave(){
        return "user/user-save";
    }

    @GetMapping("/user/search")
    public String userSearch() {
        return "user/user-search";
    }

    @GetMapping("/user/update/{id}")
    public String userUpdate(@PathVariable Long id, Model model){
        UserResponseDto dto = userService.findById(id);
        model.addAttribute("user",dto);
        return "user/user-update";
    }

}
