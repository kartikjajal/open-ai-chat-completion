package openai.learn.openai.earn.chat.completion.plugin;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import openai.learn.openai.earn.chat.completion.model.RestaurantMenu;

import java.util.*;
import java.util.stream.Collectors;

public class RestaurantPlugin {
private final Map<Integer, RestaurantMenu> menuItems = Map.of(
        1, RestaurantMenu.builder()
                .id(1)
                .item("plain dosa")
                .price(60)
                .isSugarFree(false)
                .build(),
        2, RestaurantMenu.builder()
                .id(1)
                .item("masala dosa")
                .price(60)
                .isSugarFree(false)
                .build(),
        3, RestaurantMenu.builder()
                .id(1)
                .item("idli")
                .price(60)
                .isSugarFree(false)
                .build(),
        4, RestaurantMenu.builder()
                .id(1)
                .item("sandwitch")
                .price(60)
                .isSugarFree(false)
                .build(),
        5, RestaurantMenu.builder()
                .id(1)
                .item("lassi")
                .price(60)
                .isSugarFree(true)
                .build()
);

    @DefineKernelFunction(name = "get_menu_items", description = "Gets a list of items available in the restaurant")
    public List<RestaurantMenu> getMenu() {
        System.out.println("Getting menu items");
        return new ArrayList<>(menuItems.values());
    }

    @DefineKernelFunction(name = "get_menu", description = "Gets a list of items available in the restaurant")
    public List<RestaurantMenu> getMenu(@KernelFunctionParameter(name = "item",
                                                                description = "item to search in menu") String item) {
        Collection<RestaurantMenu> filteredResult = menuItems.values()
                        .stream()
                                .filter(menuItems->menuItems.item().contains(item))
                                        .collect(Collectors.toList());
        System.out.println("Getting menu items");
        if(filteredResult.isEmpty()) {
            throw new IllegalArgumentException("Item is not available now, please select something else");
        }
        return new ArrayList<>(filteredResult);
    }

}
