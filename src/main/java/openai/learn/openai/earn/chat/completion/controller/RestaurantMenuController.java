package openai.learn.openai.earn.chat.completion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import openai.learn.openai.earn.chat.completion.model.RestaurantMenu;
import openai.learn.openai.earn.chat.completion.service.RestaurantMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantMenuController {
    @Autowired
    private RestaurantMenuService restaurantMenuService;

    @GetMapping("/menu")
    public List<String> getMenu(@RequestParam(name = "item") String item) {
        return restaurantMenuService.getMenu(item);
    }
}
