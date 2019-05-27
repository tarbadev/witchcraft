import * as RecipePage from './recipes.po'

const goTo = async () => {
  await global.page.goto(`${appUrl}/recipes/new`)
  await global.page.waitForSelector('.auto__submit-button')
}

export const addFromUrl = async (url) => {
  await goTo()
  await global.page.type('.auto__url input', url)
  await global.page.click('.auto__submit-button')
  await RecipePage.waitForPageLoaded()
}

export const addFromForm = async ({
  name, originUrl, imageUrl, ingredients, steps
}) => {
  await goTo()

  await global.page.type('.manual__name input', name)
  await global.page.type('.manual__url input', originUrl)
  await global.page.type('.manual__imageUrl input', imageUrl)
  await global.page.type('.manual__ingredients textarea[name=ingredients]', ingredients)
  await global.page.type('.manual__steps textarea[name=steps]', steps)

  await global.page.click('.manual__submit-button')
  await RecipePage.waitForPageLoaded()
}