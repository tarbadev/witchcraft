import * as NewRecipePage from '../page-objects/newRecipe.po'
import * as RecipesPage from '../page-objects/recipes.po'
import { waitForTextByCss } from '../page-objects/helpers.po'
import * as RecipePage from '../page-objects/recipe.po'

describe('New Recipe', () => {
  describe('add recipe from url', () => {
    it('retrieves details from www.cookincanuck.com', async () => {
      await NewRecipePage.addFromUrl('https://www.cookincanuck.com/mini-goat-cheese-stuffed-potato-appetizers')
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'mini goat cheese stuffed potato appetizers – greek-inspired',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)

      await RecipesPage.clickOnRecipe('mini goat cheese stuffed potato appetizers – greek-inspired')
      await waitForTextByCss('[data-recipe-title]', 'Mini Goat Cheese Stuffed Potato Appetizers – Greek-Inspired')

      const ingredients = await RecipePage.getIngredients()
      const expectedIngredients = [
        'Little Potato Co. Creamer potatoes I used Dynamic Duo',
        'Soft goat cheese chevre, room temperature',
        'Diced roasted red pepper',
        'Pitted Kalamata olives diced',
        'Minced flat-leaf parsley',
        'Pistachios halves divided',
        'Honey',
        'Ground cinnamon',
      ].sort()
      expect(ingredients).toEqual(expectedIngredients)

      const steps = await RecipePage.getSteps()
      const expectedSteps = [
        'Place the potatoes in a large saucepan and cover with cold water by an inch. Bring to a boil. Cook until the potatoes are tender when pierced with a fork, but not falling apart, 15 to 20 minutes. Drain and let the potatoes rest until cool enough to handle.',
        'While the potatoes are cooking, prepare the fillings. In the first bowl, stir together the goat cheese roasted red pepper and Kalamata olives.',
        'In the second bowl, stir together the goat cheese, 2 tablespoons pistachios and honey.',
        'Using a small, sharp knife, cut a small slice off of the bottom of each potato so that they sit flat. Cut a small circle out of the top of each potato, then scoop out some of the flesh with a ¼ teaspoon measuring spoon. Reserve the flesh and top for another use (mix into mashed potatoes!)',
        'Scoop about 1 teaspoon of the savory filling into half of the potatoes (you could choose one color of potato) and garnish with parsley. Scoop the sweet filling into the remaining half of the potatoes and garnish with the remaining pistachios.',
        'Arrange on a serving plate. Serve.',
      ]
      expect(steps.sort()).toEqual(expectedSteps.sort())

      expect(await RecipePage.getPortions()).toEqual('24')
    }, 10000)

    it('retrieves details from www.hellofresh.com', async () => {
      await NewRecipePage.addFromUrl('https://www.hellofresh.com/recipes/2019-w26-r14-baja-chicken-quesadilla-5cd9dfa0d5c2f800105388bc?locale=en-US')
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'baja chicken quesadillas',
        'lasagna alla bolognese',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)

      await RecipesPage.clickOnRecipe('baja chicken quesadillas')
      await waitForTextByCss('[data-recipe-title]', 'Baja Chicken Quesadillas')

      const ingredients = await RecipePage.getIngredients()
      const expectedIngredients = [
        'Red Onion',
        'Blackening Spice',
        'Roma Tomato',
        'Flour Tortilla',
        'Mexican Cheese Blend',
        'Chicken Breast Strips',
        'Lime',
        'Sour Cream',
        'Mozzarella Cheese',
        'Hot Sauce',
        'Vegetable Oil',
        'Butter',
        'Salt',
        'Pepper',
      ].sort()
      expect(ingredients).toEqual(expectedIngredients)

      const steps = await RecipePage.getSteps()
      const expectedSteps = [
        'Wash and dry all produce. Halve, peel, and finely dice onion. Pat chicken dry with paper towels.',
        'Set aside 2 TBSP onion (4 TBSP for 4 servings). Heat a large drizzle of oil in a large, preferably nonstick, pan over medium-high heat. Add chicken, remaining onion, Blackening Spice, salt, and pepper. Cook, stirring occasionally, until chicken is browned and cooked through, 4-6 minutes. Turn off heat; transfer to a large bowl. Wipe out pan.',
        'Meanwhile, zest and quarter lime. Finely dice tomato. In a medium bowl, combine tomato and 1 TBSP reserved onion (3 TBSP for 4 servings); add more onion to taste. Squeeze in juice from 1 lime wedge (2 lime wedges for 4) and season with salt and pepper.',
        'In a small bowl, combine sour cream, lime zest (to taste), and juice from 1 lime wedge (2 lime wedges for 4 servings). Add water, 1 tsp at a time, until mixture reaches drizzling consistency. Season with salt and pepper.',
        'Place tortillas on a work surface. Evenly sprinkle mozzarella and Mexican cheese onto one side of each tortilla. Top cheese with filling, then fold tortillas in half to create quesadillas. Heat a drizzle of oil and 1 TBSP butter (2 TBSP for 4 servings) in pan used for chicken over medium-high heat. Working in batches, add quesadillas and cook until golden brown on the first side, 1-2 minutes. Flip and cook 1 minute more. Transfer to a paper-towel-lined plate.',
        'Transfer quesadillas to a cutting board; slice into thirds and divide between plates. Serve with salsa, lime crema, and hot sauce (to taste). Serve with any remaining lime wedges on the side.',
      ]
      expect(steps.sort()).toEqual(expectedSteps.sort())

      expect(await RecipePage.getPortions()).toEqual('2')
    }, 10000)

    it('retrieves details from www.marmiton.org', async () => {
      await NewRecipePage.addFromUrl('https://www.marmiton.org/recettes/recette_pate-a-crepes_12372.aspx')
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'pâte à crêpes',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)

      await RecipesPage.clickOnRecipe('pâte à crêpes')
      await waitForTextByCss('[data-recipe-title]', 'Pâte À Crêpes')

      const ingredients = await RecipePage.getIngredients()
      const expectedIngredients = [
        'Farine',
        'Oeufs entiers',
        'Sucre',
        'D\'huile',
        'Beurre fondu',
        'Lait',
        'Rhum',
      ].sort()
      expect(ingredients).toEqual(expectedIngredients)

      const steps = await RecipePage.getSteps()
      const expectedSteps = [
        'Mettre la farine dans une terrine et former un puits.',
        'Y déposer les oeufs entiers, le sucre, l\'huile et le beurre.',
        'Mélanger délicatement avec un fouet en ajoutant au fur et à mesure le lait. La pâte ainsi obtenue doit avoir une consistance d\'un liquide légèrement épais.',
        'Parfumer de rhum.',
        'Faire chauffer une poêle antiadhésive et la huiler très légèrement. Y verser une louche de pâte, la répartir dans la poêle puis attendre qu\'elle soit cuite d\'un côté avant de la retourner. Cuire ainsi toutes les crêpes à feu doux.',
      ]
      expect(steps.sort()).toEqual(expectedSteps.sort())

      expect(await RecipePage.getPortions()).toEqual('15')
    }, 10000)
  })

  describe('add recipe from form inputs', () => {
    it('retrieves ingredient details and steps', async () => {
      await NewRecipePage.addFromForm(
        {
          name: 'Some recipe name',
          originUrl: 'http://some/originUrl/of/recipe',
          imageUrl: 'http://some/originUrl/of/recipe.png',
          ingredients: '10 tbsp sugar\n1/2 cup olive oil\n1 lemon',
          steps: 'Add ingredients and stir\nServe on each plate',
          portions: '4',
        },
      )
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'some recipe name',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)

      await RecipesPage.clickOnRecipe('some recipe name')
      await waitForTextByCss('[data-recipe-title]', 'Some Recipe Name')

      const ingredients = await RecipePage.getIngredients()
      const expectedIngredients = [
        'Sugar',
        'Olive oil',
        'Lemon',
      ].sort()
      expect(ingredients).toEqual(expectedIngredients)

      const steps = await RecipePage.getSteps()
      const expectedSteps = [
        'Add ingredients and stir',
        'Serve on each plate',
      ]
      expect(steps).toEqual(expectedSteps)

      expect(await RecipePage.getPortions()).toEqual('4')
    }, 10000)
  })

  it('displays the list of supported domains', async () => {
    await NewRecipePage.goTo()

    const expectedDomains = [
      'HelloFresh',
      'Cookin Canuck',
      'Marmiton',
    ]

    const supportedDomains = await NewRecipePage.getSupportedDomains()

    expect(supportedDomains.sort()).toEqual(expectedDomains.sort())
  })
})