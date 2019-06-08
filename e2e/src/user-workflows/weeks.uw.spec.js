import * as WeeksPage from '../page-objects/weeks.po'
import * as CartPage from '../page-objects/cart.po'

describe('Weeks page', () => {
  describe('on lunch or diner click', () => {
    it('displays a list of clickable recipes', async () => {
      await WeeksPage.goTo()
      await WeeksPage.clickOnMeal('lunch', 'THURSDAY')

      const recipes = await WeeksPage.getModalRecipeList()
      expect(recipes).toEqual([
        'lasagna alla bolognese',
        'tartiflette',
        'thai chicken salad',
      ])

      await WeeksPage.clickOnRecipe('thai chicken salad')

      const thursdayLunch = await WeeksPage.getMeal('lunch', 'THURSDAY')
      expect(thursdayLunch).toEqual('thai chicken salad')
    })

    it('saves the current week and displays a success message', async () => {
      await WeeksPage.goTo()
      await WeeksPage.clickOnMeal('lunch', 'THURSDAY')
      await WeeksPage.clickOnRecipe('thai chicken salad')
      await WeeksPage.save()

      await global.page.waitForSelector('.week-page__success-message')
      expect(await WeeksPage.isSuccessMessageDisplayed()).toBeTruthy()

      await WeeksPage.discardSuccessMessage()

      expect(await WeeksPage.isSuccessMessageDisplayed()).toBeFalsy()

      await WeeksPage.goTo()

      const thursdayLunch = await WeeksPage.getMeal('lunch', 'THURSDAY')
      expect(thursdayLunch).toEqual('thai chicken salad')
    })

    it('generates a cart from a given week', async () => {
      await WeeksPage.goTo()
      await WeeksPage.clickOnMeal('lunch', 'THURSDAY')
      await WeeksPage.clickOnRecipe('thai chicken salad')

      const thursdayLunch = await WeeksPage.getMeal('lunch', 'THURSDAY')
      expect(thursdayLunch).toEqual('thai chicken salad')

      await WeeksPage.clickOnMeal('diner', 'MONDAY')
      await WeeksPage.clickOnRecipe('lasagna alla bolognese')

      const wednesdayDiner = await WeeksPage.getMeal('diner', 'MONDAY')
      expect(wednesdayDiner).toEqual('lasagna alla bolognese')

      await WeeksPage.clickOnCreateCart()
      await CartPage.waitForPageLoaded()

      expect(global.page.url()).toContain(`${appUrl}/carts/`)

      const recipes = await CartPage.getRecipes()
      expect(recipes.sort()).toEqual([
        'thai chicken salad',
        'lasagna alla bolognese',
      ].sort())
    })
  })
})