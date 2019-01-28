import * as CartPage from '../page-objects/cart.po'
import * as NewCartPage from '../page-objects/newCart.po'

describe('New Cart', () => {
  beforeEach(async () => {
    await NewCartPage.goTo()

    await NewCartPage.selectRecipes([0, 2])
    await NewCartPage.clickOnSubmit()
    await CartPage.waitForPageLoaded()
  })

  it('displays recipes', async () => {
    const recipes = await CartPage.getRecipes()
    expect(recipes.sort()).toEqual([
      'lasagna alla bolognese',
      'thai chicken salad',
    ])
  })

  it('displays ingredients', async () => {
    const ingredients = await CartPage.getIngredients()
    expect(ingredients.sort()).toEqual([
      'agave nectar (or honey)',
      'chili garlic sauce',
      'cooked, shredded chicken breast',
      'fish sauce',
      'green onion, thinly sliced',
      'lime, juiced',
      'low-sodium soy sauce',
      'medium carrots, grated (about 1 cup)',
      'minced cilantro',
      'napa cabbage, thinly sliced (about 4 cups)',
      'natural peanut butter',
      'rice vinegar',
      'slivered almonds, toasted',
      'small red cabbage, thinly sliced (about 2 cups)',
    ])
  })
})