import * as RecipePage from '../page-objects/recipe.po'

describe('Recipe', () => {
  describe('on heart button click', () => {
    it('Adds the recipe as favourite', async () => {
      await RecipePage.goTo(3)

      expect(await RecipePage.isFavorite()).toBeFalsy()

      await RecipePage.clickOnFavoriteButton()
      await RecipePage.waitForFavoriteState()

      expect(await RecipePage.isFavorite()).toBeTruthy()
    })
  })
})