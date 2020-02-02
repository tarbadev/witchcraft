import { waitForTextNotEmptyByCss } from './helpers.po'

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('div[name="title"]')
  await waitForTextNotEmptyByCss('div[name="title"]')
}

export const getRecipes = async () => {
  await global.page.waitForSelector('a[data-recipe="true"]')
  return await global.page.$$eval(
    'a[data-recipe="true"]',
    elements => elements.map(el => el.textContent),
  )
}

export const getIngredients = async () => {
  await global.page.waitForSelector('[data-ingredient-container] [data-name]')
  return await global.page.$$eval(
    '[data-ingredient-container] [data-name]',
    elements => elements.map(el => el.textContent).sort(),
  )
}

export const tapOnIngredient = async () => {
  await global.page.waitForSelector('[data-item]', { timeout: 500 })
  await global.page.click('[data-item]')
  await global.page.waitForSelector('.cart-page__ingredient-disabled', { timeout: 500 })
}

export const numberOfDisabledItems = async () => {
  return await (await global.page.$$('.cart-page__ingredient-disabled')).length
}

export const clickOnDelete = async () => {
  await global.page.click('[data-open-menu]')
  await global.page.click('[data-delete-cart-button]')
}
