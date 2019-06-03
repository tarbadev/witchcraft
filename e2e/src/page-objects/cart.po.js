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

export const tapOnIngredient = async ingredient => {
  const ingredients = await global.page.$x(`//p[contains(text(), '${ingredient}') and contains(@class, 'ingredient')]`)

  if (await ingredients.length > 0) {
    await ingredients[0].click()
    await global.page.waitForSelector('.cart-page__ingredient-disabled')
  } else {
    throw new Error(`Ingredient not found: ${ingredient}`)
  }
}

export const numberOfDisabledItems = async () => {
  return await (await global.page.$$('.cart-page__ingredient-disabled')).length
}
