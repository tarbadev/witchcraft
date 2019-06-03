import * as CartPage from '../page-objects/cart.po'
import * as NewCartPage from '../page-objects/newCart.po'

describe('Cart', () => {
  beforeEach(async () => {
    await NewCartPage.goTo()

    await NewCartPage.selectRecipes([0, 2])
    await NewCartPage.clickOnSubmit()
    await CartPage.waitForPageLoaded()
  })

  it('changes the state of an item when clicking on it', async () => {
    expect(await CartPage.numberOfDisabledItems()).toBe(0)
    await CartPage.tapOnIngredient('fish sauce')
    expect(await CartPage.numberOfDisabledItems()).toBe(1)
  })
})