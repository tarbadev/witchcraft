package com.tarbadev.witchcraft

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.entity.Item
import com.tarbadev.witchcraft.converter.*
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step

class TestResources {
  class Recipes {
    companion object {
      val cookinCanuck = Recipe(
          name = "Mini Goat Cheese Stuffed Potato Appetizers – Greek-Inspired",
          originUrl = "https://www.cookincanuck.com/mini-goat-cheese-stuffed-potato-appetizers/",
          imgUrl = "https://www.cookincanuck.com/wp-content/uploads/2017/11/Mini-Goat-Cheese-Stuffed-Potato-Appetizers-Greek-Inspired-Cookin-Canuck-4-250x250.jpg",
          ingredients = listOf(
              Ingredient(
                  name = "Little Potato Co. Creamer potatoes (I used Dynamic Duo)",
                  quantity = 1.5.pound
              ),
              Ingredient(
                  name = "soft goat cheese (chevre), room temperature",
                  quantity = 4.ounce
              ),
              Ingredient(
                  name = "diced roasted red pepper",
                  quantity = 3.tablespoon
              ),
              Ingredient(
                  name = "pitted Kalamata olives, diced",
                  quantity = 4.unit
              ),
              Ingredient(
                  name = "minced flat-leaf parsley",
                  quantity = 1.tablespoon
              ),
              Ingredient(
                  name = "pistachios halves, divided",
                  quantity = 3.tablespoon
              ),
              Ingredient(
                  name = "honey",
                  quantity = 1.teaspoon
              ),
              Ingredient(
                  name = "ground cinnamon",
                  quantity = 0.25.teaspoon
              )
          ),
          steps = listOf(
              Step(name = "Place the potatoes in a large saucepan and cover with cold water by an inch. Bring to a boil. Cook until the potatoes are tender when pierced with a fork, but not falling apart, 15 to 20 minutes. Drain and let the potatoes rest until cool enough to handle."),
              Step(name = "While the potatoes are cooking, prepare the fillings. In the first bowl, stir together the goat cheese roasted red pepper and Kalamata olives."),
              Step(name = "In the second bowl, stir together the goat cheese, 2 tablespoons pistachios and honey."),
              Step(name = "Using a small, sharp knife, cut a small slice off of the bottom of each potato so that they sit flat. Cut a small circle out of the top of each potato, then scoop out some of the flesh with a ¼ teaspoon measuring spoon. Reserve the flesh and top for another use (mix into mashed potatoes!)"),
              Step(name = "Scoop about 1 teaspoon of the savory filling into half of the potatoes (you could choose one color of potato) and garnish with parsley. Scoop the sweet filling into the remaining half of the potatoes and garnish with the remaining pistachios."),
              Step(name = "Arrange on a serving plate. Serve.")
          ),
          portions = 24
      )
      val helloFresh = Recipe(
          name = "Baja Chicken Quesadillas",
          originUrl = "https://www.hellofresh.com/recipes/2019-w26-r14-baja-chicken-quesadilla-5cd9dfa0d5c2f800105388bc?locale=en-US",
          imgUrl = "https://img.hellofresh.com/f_auto,fl_lossy,q_auto,w_1200/hellofresh_s3/image/2019-w26-r14-baja-chicken-quesadilla-e4f0ad1b.jpg",
          ingredients = listOf(
              Ingredient(
                  name = "Red Onion",
                  quantity = 1.unit
              ),
              Ingredient(
                  name = "Blackening Spice",
                  quantity = 1.tablespoon
              ),
              Ingredient(
                  name = "Roma Tomato",
                  quantity = 1.unit
              ),
              Ingredient(
                  name = "Flour Tortilla",
                  quantity = 2.unit
              ),
              Ingredient(
                  name = "Mexican Cheese Blend",
                  quantity = 0.5.cup
              ),
              Ingredient(
                  name = "Chicken Breast Strips",
                  quantity = 10.ounce
              ),
              Ingredient(
                  name = "Lime",
                  quantity = 1.unit
              ),
              Ingredient(
                  name = "Sour Cream",
                  quantity = 2.tablespoon
              ),
              Ingredient(
                  name = "Mozzarella Cheese",
                  quantity = 0.5.cup
              ),
              Ingredient(
                  name = "Hot Sauce",
                  quantity = 1.teaspoon
              ),
              Ingredient(
                  name = "Vegetable Oil",
                  quantity = 4.teaspoon
              ),
              Ingredient(
                  name = "Butter",
                  quantity = 2.tablespoon
              ),
              Ingredient(
                  name = "Salt",
                  quantity = 1.unit
              ),
              Ingredient(
                  name = "Pepper",
                  quantity = 1.unit
              )
          ),
          steps = listOf(
              Step(name = "Wash and dry all produce. Halve, peel, and finely dice onion. Pat chicken dry with paper towels."),
              Step(name = "Set aside 2 TBSP onion (4 TBSP for 4 servings). Heat a large drizzle of oil in a large, preferably nonstick, pan over medium-high heat. Add chicken, remaining onion, Blackening Spice, salt, and pepper. Cook, stirring occasionally, until chicken is browned and cooked through, 4-6 minutes. Turn off heat; transfer to a large bowl. Wipe out pan."),
              Step(name = "Meanwhile, zest and quarter lime. Finely dice tomato. In a medium bowl, combine tomato and 1 TBSP reserved onion (3 TBSP for 4 servings); add more onion to taste. Squeeze in juice from 1 lime wedge (2 lime wedges for 4) and season with salt and pepper."),
              Step(name = "In a small bowl, combine sour cream, lime zest (to taste), and juice from 1 lime wedge (2 lime wedges for 4 servings). Add water, 1 tsp at a time, until mixture reaches drizzling consistency. Season with salt and pepper."),
              Step(name = "Place tortillas on a work surface. Evenly sprinkle mozzarella and Mexican cheese onto one side of each tortilla. Top cheese with filling, then fold tortillas in half to create quesadillas. Heat a drizzle of oil and 1 TBSP butter (2 TBSP for 4 servings) in pan used for chicken over medium-high heat. Working in batches, add quesadillas and cook until golden brown on the first side, 1-2 minutes. Flip and cook 1 minute more. Transfer to a paper-towel-lined plate."),
              Step(name = "Transfer quesadillas to a cutting board; slice into thirds and divide between plates. Serve with salsa, lime crema, and hot sauce (to taste). Serve with any remaining lime wedges on the side.")
          ),
          portions = 2
      )

      val marmiton = Recipe(
          name = "Pâte à crêpes",
          originUrl = "https://www.marmiton.org/recettes/recette_pate-a-crepes_12372.aspx",
          imgUrl = "https://assets.afcdn.com/recipe/20180713/81162_w420h344c1cx1944cy2592cxt0cyt0cxb3888cyb5184.jpg",
          ingredients = listOf(
              Ingredient(
                  name = "Farine",
                  quantity = 300.gram
              ),
              Ingredient(
                  name = "Oeufs entiers",
                  quantity = 3.unit
              ),
              Ingredient(
                  name = "Sucre",
                  quantity = 3.tablespoon
              ),
              Ingredient(
                  name = "Huile",
                  quantity = 2.tablespoon
              ),
              Ingredient(
                  name = "Beurre fondu",
                  quantity = 50.gram
              ),
              Ingredient(
                  name = "Lait",
                  quantity = 60.centiliter
              ),
              Ingredient(
                  name = "Rhum",
                  quantity = 5.centiliter
              )
          ),
          steps = listOf(
              Step(name = "Mettre la farine dans une terrine et former un puits."),
              Step(name = "Y déposer les oeufs entiers, le sucre, l'huile et le beurre."),
              Step(name = "Mélanger délicatement avec un fouet en ajoutant au fur et à mesure le lait. La pâte ainsi obtenue doit avoir une consistance d'un liquide légèrement épais."),
              Step(name = "Parfumer de rhum."),
              Step(name = "Faire chauffer une poêle antiadhésive et la huiler très légèrement. Y verser une louche de pâte, la répartir dans la poêle puis attendre qu'elle soit cuite d'un côté avant de la retourner. Cuire ainsi toutes les crêpes à feu doux.")
          ),
          portions = 15
      )
    }
  }

  class Items {
    companion object {
      val itemLardon = Item(
          id = 12,
          name = "Lardon",
          quantity = 12.0,
          unit = "g",
          enabled = true
      )
    }
  }

  companion object {
    val cart = Cart(
        id = 43,
        recipes = listOf(Recipes.helloFresh, Recipes.marmiton),
        items = listOf(Items.itemLardon)
    )
  }
}
