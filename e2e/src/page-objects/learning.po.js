import { goToUrl } from './helpers.po'

export const goTo = async () => {
  await goToUrl(`/learning`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('[data-ingredient]')
}

export const getIngredients = async () => {
  const ingredientElements = await global.page.$$('[data-ingredient]')

  return await Promise.all(ingredientElements.map(async (element) => {
    const line = await element.$eval('[data-ingredient-line] input', element => element.value)
    const name = await element.$eval('[data-ingredient-name] input', element => element.value)
    const quantity = await element.$eval('[data-ingredient-quantity] input', element => element.value)
    const unit = await element.$eval('[data-ingredient-unit] input', element => element.value)
    const language = await element.$eval('[data-ingredient-language] div', element => element.textContent)
    const detail = await element.$eval('[data-ingredient-detail] input', element => element.value)
    return { line, name, quantity, unit, language, detail }
  }))
}

export const switchToValidatedTab = async () => {
  await global.page.click('[data-tab-validated]')
  await global.page.waitForSelector('[data-validated-container]')
}

export const validateFirstIngredient = async () => {
  await global.page.click('[data-ingredient-validate]')
  await page.waitForResponse(response => response.url().includes('/api/learning') && response.status() === 200)
}
