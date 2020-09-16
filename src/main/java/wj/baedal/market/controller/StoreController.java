package wj.baedal.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wj.baedal.market.controller.dto.store.StoreResponseDto;
import wj.baedal.market.controller.dto.store.StoreSearchResponseDto;
import wj.baedal.market.entity.menu.Menu;
import wj.baedal.market.entity.menu.MenuRepository;
import wj.baedal.market.service.storeservice.StoreService;

import java.util.List;

@Profile("local")
@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/store/save")
    public String storeSave(){
        return "store/store-save";
    }

    @GetMapping("/store/search")
    public String storeSearch(){
        return "store/store-search";
    }

    @GetMapping("/store/update/{id}")
    public String storeUpdate(@PathVariable Long id, Model model){
        StoreSearchResponseDto store = storeService.findById2(id);
        model.addAttribute("store",store);
        return "store/store-update";
    }
}
