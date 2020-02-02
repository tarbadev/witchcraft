import * as CartPage from '../page-objects/cart.po'
import * as CartsPage from '../page-objects/carts.po'
import * as NewCartPage from '../page-objects/newCart.po'

describe('Cart', () => {
  const createCart = async () => {
    await NewCartPage.goTo()

    await NewCartPage.selectRecipes([0, 2])
    await NewCartPage.clickOnSubmit()
    await CartPage.waitForPageLoaded()
  }

  it('Carts page displays a button to the new cart page', async () => {
    await CartsPage.goTo()
    await CartsPage.newCart()

    expect(global.page.url()).toBe(`${appUrl}/carts/new`)
  })

  it('changes the state of an item when clicking on it', async () => {
    await createCart()

    expect(await CartPage.numberOfDisabledItems()).toBe(0)
    await CartPage.tapOnIngredient('fish sauce')
    expect(await CartPage.numberOfDisabledItems()).toBe(1)
  })

  it('contains a list of carts', async () => {
    await CartsPage.goTo()
    expect(await CartsPage.getCarts()).toHaveLength(0)

    await createCart()
    await createCart()

    await CartsPage.goTo()

    expect(await CartsPage.getCarts()).toHaveLength(2)
  })

  it('can be deleted', async () => {
    await CartsPage.goTo()
    expect(await CartsPage.getCarts()).toHaveLength(0)

    await createCart()
    await CartPage.clickOnDelete()
    await CartsPage.waitForPageLoaded()

    expect(await CartsPage.getCarts()).toHaveLength(0)
  })
})