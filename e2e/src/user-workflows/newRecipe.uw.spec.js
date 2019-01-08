import * as NewRecipePage from '../page-objects/newRecipe.po'
import * as RecipesPage from '../page-objects/recipes.po'

describe('New Recipe', () => {
  describe('add recipe from url', () => {
    it('retrieves details from html page', async () => {
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