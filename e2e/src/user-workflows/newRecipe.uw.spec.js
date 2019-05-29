import * as NewRecipePage from '../page-objects/newRecipe.po'
import * as RecipesPage from '../page-objects/recipes.po'

describe('New Recipe', () => {
  describe('add recipe from url', () => {
    it('retrieves details from www.cookincanuck.com', async () => {
      await NewRecipePage.addFromUrl('https://www.cookincanuck.com/ragu-stuffed-portobello-mushrooms')
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'ragu stuffed portobello mushrooms',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)
    })

    it('retrieves details from www.hellofresh.com', async () => {
      await NewRecipePage.addFromUrl('https://www.hellofresh.com/recipes/2019-w26-r14-baja-chicken-quesadilla-5cd9dfa0d5c2f800105388bc?locale=en-US')
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'baja chicken quesadilla',
        'lasagna alla bolognese',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)
    })

    it('retrieves details from www.marmiton.org', async () => {
      await NewRecipePage.addFromUrl('https://www.marmiton.org/recettes/recette_pate-a-crepes_12372.aspx')
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'Pâte à crêpes',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)
    })
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
    })
  })
})