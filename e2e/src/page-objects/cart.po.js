import { waitForTextNotEmptyByCss } from './helpers.po'

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('div[name="title"]')
  await waitForTextNotEmptyByCss('div[name="title"]')
}

export const getRecipes = async () => {
  return await global.page.$$eval(
    'a[data-recipe="true"]',
    elements => elements.map(el => el.textContent),
  )
}

export const getIngredients = async () => {
  return await global.page.$$eval(
    'p.ingredient',
    elements => elements.map(el => el.textContent),
  )
}