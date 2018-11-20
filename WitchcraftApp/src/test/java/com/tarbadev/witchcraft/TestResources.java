package com.tarbadev.witchcraft;

import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Step;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;

@ConfigurationProperties(prefix = "test-resources")
@Getter
@Setter
public class TestResources {
  private Recipe recipe = Recipe.builder()
      .name("Mini Goat Cheese Stuffed Potato Appetizers – Greek-Inspired")
      .originUrl("https://www.cookincanuck.com/mini-goat-cheese-stuffed-potato-appetizers/")
      .imgUrl("https://www.cookincanuck.com/wp-content/uploads/2017/11/Mini-Goat-Cheese-Stuffed-Potato-Appetizers-Greek-Inspired-Cookin-Canuck-4.jpg")
      .ingredients(Arrays.asList(
          Ingredient.builder()
              .name("Little Potato Co. Creamer potatoes (I used Dynamic Duo)")
              .quantity(1.5)
              .unit("lb")
              .build(),
          Ingredient.builder()
              .name("soft goat cheese (chevre), room temperature")
              .quantity(4.0)
              .unit("oz")
              .build(),
          Ingredient.builder()
              .name("diced roasted red pepper")
              .quantity(3.0)
              .unit("tbsp")
              .build(),
          Ingredient.builder()
              .name("pitted Kalamata olives, diced")
              .quantity(4.0)
              .unit("")
              .build(),
          Ingredient.builder()
              .name("minced flat-leaf parsley")
              .quantity(1.0)
              .unit("tbsp")
              .build(),
          Ingredient.builder()
              .name("pistachios halves, divided")
              .quantity(3.0)
              .unit("tbsp")
              .build(),
          Ingredient.builder()
              .name("honey")
              .quantity(1.0)
              .unit("tsp")
              .build(),
          Ingredient.builder()
              .name("ground cinnamon")
              .quantity(0.25)
              .unit("tsp")
              .build()
      ))
      .steps(Arrays.asList(
          Step.builder()
              .name("Place the potatoes in a large saucepan and cover with cold water by an inch. Bring to a boil. Cook until the potatoes are tender when pierced with a fork, but not falling apart, 15 to 20 minutes. Drain and let the potatoes rest until cool enough to handle.")
              .build(),
          Step.builder()
              .name("While the potatoes are cooking, prepare the fillings. In the first bowl, stir together the goat cheese roasted red pepper and Kalamata olives.")
              .build(),
          Step.builder()
              .name("In the second bowl, stir together the goat cheese, 2 tablespoons pistachios and honey.")
              .build(),
          Step.builder()
              .name("Using a small, sharp knife, cut a small slice off of the bottom of each potato so that they sit flat. Cut a small circle out of the top of each potato, then scoop out some of the flesh with a ¼ teaspoon measuring spoon. Reserve the flesh and top for another use (mix into mashed potatoes!)")
              .build(),
          Step.builder()
              .name("Scoop about 1 teaspoon of the savory filling into half of the potatoes (you could choose one color of potato) and garnish with parsley. Scoop the sweet filling into the remaining half of the potatoes and garnish with the remaining pistachios.")
              .build(),
          Step.builder()
              .name("Arrange on a serving plate. Serve.")
              .build()
      ))
      .build();
}
