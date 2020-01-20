import * as WeeksPage from '../page-objects/weeks.po'
import * as CartPage from '../page-objects/cart.po'

describe('Weeks page', () => {
  describe('on add recipe click', () => {
    it('displays a list of clickable recipes and removes it if clicked again', async () => {
      await WeeksPage.goTo()
      await WeeksPage.clickOnAddRecipe('lunch', 'THURSDAY')

      const recipes = await WeeksPage.getModalRecipeList()
      expect(recipes).toEqual([
        'lasagna alla bolognese',
        'tartiflette',
        'thai chicken salad',
      ])

      await WeeksPage.clickOnRecipe('thai chicken salad')
      expect(await WeeksPage.getMeal('lunch', 'THURSDAY')).toEqual('thai chicken salad')

      await WeeksPage.clickOnAddRecipe('lunch', 'THURSDAY')
      await WeeksPage.clickOnRecipe('thai chicken salad')
      expect(await WeeksPage.getMeal('lunch', 'THURSDAY')).toEqual('')
    })

    it('adds an express recipe', async () => {
      await WeeksPage.goTo()
      await WeeksPage.clickOnAddRecipe('diner', 'MONDAY')
      await WeeksPage.addExpressRecipe('Fajitas With Beef')

      const mondayDiner = await WeeksPage.getMeal('diner', 'MONDAY')
      expect(mondayDiner).toEqual('fajitas with beef')
    })
  })

  it('saves the current week and displays a success message', async () => {
    await WeeksPage.goTo()
    await WeeksPage.clickOnAddRecipe('lunch', 'THURSDAY')
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

  describe('on create cart click', () => {
    it('saves the cart when clicking on create cart', async () => {
      await WeeksPage.goTo()
      await WeeksPage.clickOnAddRecipe('lunch', 'THURSDAY')
      await WeeksPage.clickOnRecipe('thai chicken salad')
      await WeeksPage.clickOnAddRecipe('diner', 'MONDAY')
      await WeeksPage.clickOnRecipe('lasagna alla bolognese')

      await WeeksPage.clickOnCreateCart()
      await CartPage.waitForPageLoaded()

      await WeeksPage.goTo()

      const thursdayLunch = await WeeksPage.getMeal('lunch', 'THURSDAY')
      expect(thursdayLunch).toEqual('thai chicken salad')

      const wednesdayDiner = await WeeksPage.getMeal('diner', 'MONDAY')
      expect(wednesdayDiner).toEqual('lasagna alla bolognese')
    })

    it('generates a cart from a given week', async () => {
      await WeeksPage.goTo()
      await WeeksPage.clickOnAddRecipe('lunch', 'THURSDAY')
      await WeeksPage.clickOnRecipe('thai chicken salad')

      const thursdayLunch = await WeeksPage.getMeal('lunch', 'THURSDAY')
      expect(thursdayLunch).toEqual('thai chicken salad')

      await WeeksPage.clickOnAddRecipe('diner', 'MONDAY')
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