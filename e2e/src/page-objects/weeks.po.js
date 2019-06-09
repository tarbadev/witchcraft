import { goToUrl } from './helpers.po'

export const clickOnCreateCart = async () => {
  await global.page.click('.week-page__create-cart-button')
}

export const getMeal = async (meal, day) => {
  let selector = `td[data-meal="${meal}-${day}"]`

  await global.page.waitForSelector(selector)

  return await global.page.$eval(
    selector,
    element => element.textContent,
  )
}

export const clickOnMeal = async (meal, day) => {
  const selectedMeal = await global.page.$(`td[data-meal="${meal}-${day}"]`)
  await selectedMeal.click()
}

export const clickOnRecipe = async recipeName => {
  const recipes = await global.page.$x(`//div[contains(@class, 'recipe-card-title') and contains(.//div, '${recipeName}')]`)

  if (await recipes.length > 0) {
    await recipes[0].click()
  } else {
    throw new Error(`Recipe not found: ${recipeName}`)
  }
}

export const goTo = async () => {
  await goToUrl('/weeks')
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('.week-page__create-cart-button')
}

export const save = async () => {
  const saveButton = await global.page.$('.week-page__save-button')
  await saveButton.click()
}

export const getModalRecipeList = async () => {
  return await global.page.$$eval(
    'div.recipe-card-title',
    elements => elements.map(el => el.textContent),
  )
}

export const isSuccessMessageDisplayed = async () => {
  return await global.page.$('.week-page__success-message') !== null
}

export const discardSuccessMessage = async () => {
  await global.page.click('.week-page__success-message-close')
  await global.page.waitFor(200)
}

export const addExpressRecipe = async (recipeName) => {
  await global.page.click('.week-page__add-express-recipe__button')
  await global.page.type('.express-recipe-form__recipe-name input', recipeName)
  await global.page.click('.express-recipe-form__submit-button')
  await global.page.waitFor(200)
}