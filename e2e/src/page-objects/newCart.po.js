export const goTo = async () => {
  await global.page.goto(`${appUrl}/carts/new`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('.new-cart-page__generate-button')
}

export const clickOnSubmit = async () => {
  await global.page.click('.new-cart-page__generate-button')
}

export const selectRecipes = async recipeIds => {
  for (let i = 0; i < recipeIds.length; i++) {
    await global.page.click(`.new-cart-page__switch-recipe-${recipeIds[i]}`)
  }
}