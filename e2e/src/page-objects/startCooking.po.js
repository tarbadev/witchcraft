import { getTextByCssSelector } from './helpers.po'

const titleSelector = '[data-start-cooking-title]'
const ingredientsSelector = '[data-start-cooking-ingredients] [data-ingredient-container]'
const ingredientNameSelector = '[data-name]'
const ingredientQuantitySelector = '[data-quantity]'
const stepSelector = '[data-start-cooking-step]'

export const waitForComponentLoaded = async () => {
  await global.page.waitForSelector(titleSelector)
}

export const getTitle = async () => await getTextByCssSelector(titleSelector)

export const getIngredients = async () => {
  const ingredientElements = await global.page.$$(ingredientsSelector)

  return await Promise.all(ingredientElements.map(async (element) => {
    const name = await element.$eval(ingredientNameSelector, element => element.textContent)
    const quantity = await element.$eval(ingredientQuantitySelector, element => element.textContent)
    return { name, quantity }
  }))
}

export const getStep = async () => await getTextByCssSelector(stepSelector)
