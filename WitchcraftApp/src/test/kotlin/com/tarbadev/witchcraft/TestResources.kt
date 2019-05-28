package com.tarbadev.witchcraft

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import java.util.Arrays

class TestResources {
  class Recipes {
    companion object {
      val cookinCanuck = Recipe(
          name = "Mini Goat Cheese Stuffed Potato Appetizers – Greek-Inspired",
          originUrl = "https://www.cookincanuck.com/mini-goat-cheese-stuffed-potato-appetizers/",
          imgUrl = "https://www.cookincanuck.com/wp-content/uploads/2017/11/Mini-Goat-Cheese-Stuffed-Potato-Appetizers-Greek-Inspired-Cookin-Canuck-4.jpg",
          ingredients = Arrays.asList(
              Ingredient(
                  name = "Little Potato Co. Creamer potatoes (I used Dynamic Duo)",
                  quantity = 1.5,
                  unit = "lb"
              ),
              Ingredient(
                  name = "soft goat cheese (chevre), room temperature",
                  quantity = 4.0,
                  unit = "oz"
              ),
              Ingredient(
                  name = "diced roasted red pepper",
                  quantity = 3.0,
                  unit = "tbsp"
              ),
              Ingredient(
                  name = "pitted Kalamata olives, diced",
                  quantity = 4.0,
                  unit = ""
              ),
              Ingredient(
                  name = "minced flat-leaf parsley",
                  quantity = 1.0,
                  unit = "tbsp"
              ),
              Ingredient(
                  name = "pistachios halves, divided",
                  quantity = 3.0,
                  unit = "tbsp"
              ),
              Ingredient(
                  name = "honey",
                  quantity = 1.0,
                  unit = "tsp"
              ),
              Ingredient(
                  name = "ground cinnamon",
                  quantity = 0.25,
                  unit = "tsp"
              )
          ),
          steps = Arrays.asList(
              Step(name = "Place the potatoes in a large saucepan and cover with cold water by an inch. Bring to a boil. Cook until the potatoes are tender when pierced with a fork, but not falling apart, 15 to 20 minutes. Drain and let the potatoes rest until cool enough to handle."),
              Step(name = "While the potatoes are cooking, prepare the fillings. In the first bowl, stir together the goat cheese roasted red pepper and Kalamata olives."),
              Step(name = "In the second bowl, stir together the goat cheese, 2 tablespoons pistachios and honey."),
              Step(name = "Using a small, sharp knife, cut a small slice off of the bottom of each potato so that they sit flat. Cut a small circle out of the top of each potato, then scoop out some of the flesh with a ¼ teaspoon measuring spoon. Reserve the flesh and top for another use (mix into mashed potatoes!)"),
              Step(name = "Scoop about 1 teaspoon of the savory filling into half of the potatoes (you could choose one color of potato) and garnish with parsley. Scoop the sweet filling into the remaining half of the potatoes and garnish with the remaining pistachios."),
              Step(name = "Arrange on a serving plate. Serve.")
          )
      )

      val helloFresh = Recipe(
          name = "Baja Chicken Quesadilla",
          originUrl = "https://www.hellofresh.com/recipes/2019-w26-r14-baja-chicken-quesadilla-5cd9dfa0d5c2f800105388bc?locale=en-US",
          imgUrl = "https://res.cloudinary.com/hellofresh/image/upload/f_auto,fl_lossy,q_auto,w_1200/v1/hellofresh_s3/image/2019-w26-r14-baja-chicken-quesadilla-e4f0ad1b.jpg",
          ingredients = Arrays.asList(
              Ingredient(
                  name = "Red Onion",
                  quantity = 1.0
              ),
              Ingredient(
                  name = "Blackening Spice",
                  quantity = 1.0,
                  unit = "tbsp"
              ),
              Ingredient(
                  name = "Roma Tomato",
                  quantity = 1.0
              ),
              Ingredient(
                  name = "Flour Tortilla",
                  quantity = 2.0
              ),
              Ingredient(
                  name = "Mexican Cheese Blend",
                  quantity = 0.5,
                  unit = "cup"
              ),
              Ingredient(
                  name = "Chicken Breast Strips",
                  quantity = 10.0,
                  unit = "oz"
              ),
              Ingredient(
                  name = "Lime",
                  quantity = 1.0
              ),
              Ingredient(
                  name = "Sour Cream",
                  quantity = 2.0,
                  unit = "tbsp"
              ),
              Ingredient(
                  name = "Mozzarella Cheese",
                  quantity = 0.5,
                  unit = "cup"
              ),
              Ingredient(
                  name = "Hot Sauce",
                  quantity = 1.0,
                  unit = "tsp"
              ),
              Ingredient(
                  name = "Vegetable Oil",
                  quantity = 4.0,
                  unit = "tsp"
              ),
              Ingredient(
                  name = "Butter",
                  quantity = 2.0,
                  unit = "tbsp"
              ),
              Ingredient(
                  name = "Salt",
                  quantity = 1.0
              ),
              Ingredient(
                  name = "Pepper",
                  quantity = 1.0
              )
          ),
          steps = Arrays.asList(
              Step(name = "Wash and dry all produce. Halve, peel, and finely dice onion; set aside 2 TBSP (4 TBSP for 4 servings). Pat chicken dry with paper towels."),
              Step(name = "Heat a large drizzle of oil in a large pan over medium-high heat (use a nonstick pan if you have one). Add chicken, remaining diced onion, Blackening Spice, salt, and pepper. Cook, stirring occasionally, until chicken is browned and cooked through, 5-7 minutes. Turn off heat; transfer to a large bowl. Wipe out pan."),
              Step(name = "Meanwhile, zest and quarter lime. Finely dice tomato. In a medium bowl, combine tomato and 1 TBSP reserved onion (3 TBSP for 4 servings); add more onion to taste. Squeeze in juice from 1 lime wedge (2 lime wedges for 4 servings) and season with salt and pepper."),
              Step(name = "In a small bowl, combine sour cream, lime zest (to taste), and juice from 1 lime wedge (2 lime wedges for 4 servings). Add water, 1 tsp at a time, until mixture reaches drizzling consistency. Season with salt and pepper."),
              Step(name = "Place tortillas on a work surface. Evenly sprinkle mozzarella and Mexican cheese onto 1 side of each tortilla. Top cheese with filling, then fold tortillas in half to create quesadillas. Heat a drizzle of oil and 1 TBSP butter (2 TBSP for 4 servings) in pan used for chicken over medium-high heat. Working in batches, add quesadillas and cook until golden brown on the first side, 1-2 minutes. Flip and cook 1 minute more. Transfer to a paper-towel-lined plate."),
              Step(name = "Transfer quesadillas to a cutting board; slice into thirds and divide between plates. Serve with salsa, lime crema, and hot sauce (to taste). Serve with any remaining lime wedges on the side for squeezing over.")
          )
      )
    }
  }
}
