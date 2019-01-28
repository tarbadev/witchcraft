import { page } from '../setupE2eTests'

export const waitForPageLoaded = async () => {
  await page.waitForSelector('div[name="title"]')
}

export const getRecipes = async () => {
  return await page.$$eval(
    'a[data-recipe="true"]',
    elements => elements.map(el => el.textContent),
  )
}

export const getIngredients = async () => {
  return await page.$$eval(
    'p.ingredient',
    elements => elements.map(el => el.textContent),
  )
}