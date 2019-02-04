import { AppUrl, page } from '../setupE2eTests'

export const clickOnCreateCart = async () => {
  await page.click('.week-page__create-cart-button')
}

export const getMeal = async (meal, day) => {
  return await page.$eval(
    `td[data-meal="${meal}-${day}"]`,
    element => element.textContent,
  )
}

export const clickOnMeal = async (meal, day) => {
  const selectedMeal = await page.$(`td[data-meal="${meal}-${day}"]`)
  await selectedMeal.click()
}

export const clickOnRecipe = async recipeName => {
  const recipes = await page.$x(`//div[contains(@class, 'recipe-card-title') and contains(.//div, '${recipeName}')]`)

  if (await recipes.length > 0) {
    await recipes[0].click()
  } else {
    throw new Error(`Recipe not found: ${recipeName}`)
  }
}

export const goTo = async () => {
  await page.goto(`${AppUrl}/weeks`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await page.waitForSelector('.week-page__create-cart-button')
}

export const getModalRecipeList = async () => {
  return await page.$$eval(
    'div.recipe-card-title',
    elements => elements.map(el => el.textContent),
  )
}