import { AppUrl, page } from '../setupE2eTests'
import * as RecipesPage from '../page-objects/recipes.po'
import * as RecipePage from '../page-objects/recipe.po'

describe('Recipes', () => {
  describe('Recipe List', () => {
    it('displays a recipe list', async () => {
      await RecipesPage.goTo()
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)
    })
  })

  describe('Search', () => {
    it('displays filtered recipes', async () => {
      await RecipesPage.goTo()
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)

      await RecipesPage.search('thai')

      const filteredRecipes = await RecipesPage.getRecipes()
      expect(filteredRecipes).toEqual(['thai chicken salad'])
    })
  })

  describe('Navigation to Recipe Page', () => {
    it('displays recipe page when recipe item clicked', async () => {
      await RecipesPage.goTo()
      await RecipesPage.clickOnRecipe('thai chicken salad')
      await RecipePage.waitForPageLoaded()

      expect(page.url()).toBe(`${AppUrl}/recipes/3`)
    })
  })
})