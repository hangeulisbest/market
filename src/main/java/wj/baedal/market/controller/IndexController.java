package wj.baedal.market.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wj.baedal.market.controller.dto.user.UserResponseDto;
import wj.baedal.market.repository.user.UserSearchCondition;
import wj.baedal.market.service.userservice.UserService;

import java.awt.print.Pageable;

@Profile("local")
@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
