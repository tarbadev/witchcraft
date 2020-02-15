import * as RecipesPage from './recipes.po'
import { goToUrl, waitForTextByCss } from './helpers.po'

export const goTo = async () => {
  await goToUrl('/recipes/new')
  await waitForTextByCss('.witchcraft-title', 'New Recipe')
}

export const addFromUrl = async (url) => {
  await goTo()
  await global.page.type('.auto__url input', url)
  await global.page.click('[data-tag="auto-url-submit-button"]')
  await RecipesPage.waitForPageLoaded()
}

export const addFromForm = async ({
  name, originUrl, imageUrl, ingredients, steps, portions
}) => {
  await goTo()

  await global.page.type('.manual__name input', name)
  await global.page.type('.manual__url input', originUrl)
  await global.page.type('.manual__image-url input', imageUrl)
  await global.page.type('.manual__ingredients textarea[name=ingredients]', ingredients)
  await global.page.type('.manual__steps textarea[name=steps]', steps)
  await global.page.type('.manual__portions input', portions)

  await global.page.click('[data-tag="manual-url-submit-button"]')
  await RecipesPage.waitForPageLoaded()
}

export const getSupportedDomains = async () => {
  return await global.page.$$eval(
    '.supported-domains__domain-image',
    elements => elements.map(el => el.title),
  )
}
