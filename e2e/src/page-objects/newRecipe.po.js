import { AppUrl, page } from '../setupE2eTests'
import * as RecipePage from './recipes.po'

const goTo = async () => {
  await page.goto(`${AppUrl}/recipes/new`)
  await page.waitForSelector('.auto__submit-button')
}

export const addFromUrl = async (url) => {
  await goTo()
  await page.type('.auto__url input', url)
  await page.click('.auto__submit-button')
  await RecipePage.waitForPageLoaded()
}

export const addFromForm = async ({
  name, originUrl, imageUrl, ingredients, steps
}) => {
  await goTo()

  await page.type('.manual__name input', name)
  await page.type('.manual__url input', originUrl)
  await page.type('.manual__imageUrl input', imageUrl)
  await page.type('.manual__ingredients textarea[name=ingredients]', ingredients)
  await page.type('.manual__steps textarea[name=steps]', steps)

  await page.click('.manual__submit-button')
  await RecipePage.waitForPageLoaded()
}