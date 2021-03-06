import { getTextByCssSelector, goToUrl } from './helpers.po'

export const clickOnCreateCart = async () => {
  await global.page.click('[data-open-menu]')
  await global.page.waitForSelector('[data-create-cart-button]')
  await global.page.click('[data-create-cart-button]')
}

export const getMeal = async (meal, day) => {
  let selector = `td[data-meal="${meal}-${day}"]`

  await global.page.waitForSelector(selector)

  return await getTextByCssSelector(selector)
}

export const clickOnAddRecipe = async (meal, day) => {
  const maxRetries = 3
  for (let i = 0; i < maxRetries; i++) {
    try {
      const selectedMeal = await global.page.$(`td[data-meal="${meal}-${day}"] [data-add-recipe]`)
      await selectedMeal.click()
      await global.page.waitForSelector('[data-modal-recipe-container] .card-header-title', { timeout: 500 })
      break
    } catch (e) {
      if (i === maxRetries - 1) {
        throw e
      }
    }
  }
}

export const clickOnRecipe = async recipeName => {
  const maxRetries = 3
  for (let i = 0; i < maxRetries; i++) {
    try {
      const recipes = await global.page.$x(`//span[contains(@class, 'card-header-title') and contains(text(), '${recipeName}')]`)
      await recipes[recipes.length - 1].click()
      await global.page.waitForSelector('[data-modal-recipe-container] .card-header-title', { timeout: 500, hidden: true })
      break
    } catch (e) {
      if (i === maxRetries - 1) {
        throw new Error(`Recipe not found: ${recipeName}`)
      }
    }
  }
}

export const goTo = async () => {
  await goToUrl('/weeks')
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('[data-add-recipe]')
}

export const save = async () => {
  const saveButton = await global.page.$('.week-page__save-button')
  await saveButton.click()
}

export const getModalRecipeList = async () => {
  return await global.page.$$eval(
    'span.card-header-title',
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
  await global.page.type('[data-express-recipe-form-recipe-name] input', recipeName)
  await global.page.click('[data-express-recipe-form-submit-button]')
  await global.page.waitFor(500)
}