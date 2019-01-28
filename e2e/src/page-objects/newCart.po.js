import { AppUrl, page } from '../setupE2eTests'

export const goTo = async () => {
  await page.goto(`${AppUrl}/carts/new`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await page.waitForSelector('.new-cart-page__generate-button')
}

export const clickOnSubmit = async () => {
  await page.click('.new-cart-page__generate-button')
}

export const selectRecipes = async recipeIds => {
  for (let i = 0; i < recipeIds.length; i++) {
    await page.click(`.new-cart-page__switch-recipe-${recipeIds[i]}`)
  }
}