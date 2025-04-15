package openai.learn.openai.earn.chat.completion.model;

import lombok.Builder;

@Builder
public record RestaurantMenu(int id, String item, long price, String ingredients, boolean isSugarFree){

}
