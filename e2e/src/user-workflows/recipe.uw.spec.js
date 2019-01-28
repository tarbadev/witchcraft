import { AppUrl, page } from '../setupE2eTests'
import * as RecipePage from '../page-objects/recipe.po'
import * as RecipesPage from '../page-objects/recipes.po'

describe('Recipe', () => {
  describe('on heart button click', () => {
    it('adds the recipe as favourite', async () => {
      await RecipePage.goTo(3)

      expect(await RecipePage.isFavorite()).toBeFalsy()

      await RecipePage.clickOnFavoriteButton()
      await RecipePage.waitForFavoriteState()

      expect(await RecipePage.isFavorite()).toBeTruthy()
    })
  })

  describe('on delete button click', () => {
    it('delete the recipe and redirect to recipe list', async () => {
      await RecipePage.goTo(2)

      await RecipePage.clickOnDeleteButton()
      await RecipesPage.waitForPageLoaded()

      expect(page.url()).toBe(`${AppUrl}/recipes`)

      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)
    })
  })
})