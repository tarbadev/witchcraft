import { fillInput, getTextByCssSelector, goToUrl, waitForTextByCss, waitForTextNotEmptyByCss } from './helpers.po'

export const goTo = async (id, title) => {
  await goToUrl(`/recipes/${id}`)
  await waitForTitleDisplayed(title)
}

export const waitForTitleDisplayed = async title => await waitForTextByCss('[data-recipe-title]', title)

export const isFavorite = async () => {
  const favoriteButton = await global.page.$('[data-recipe-favorited]')
  return favoriteButton !== null
}

export const clickOnFavoriteButton = async () => {
  await global.page.click('[data-toggle-favorite-button]')
}

export const clickOnModifyButton = async () => {
  await global.page.click('[data-open-menu]')
  await global.page.click('[data-edit-button]')
}

export const clickOnDeleteButton = async () => {
  await global.page.click('[data-open-menu]')
  await global.page.click('[data-delete-button]')
}

export const startCooking = async () => {
  await global.page.click('[data-open-menu]')
  await global.page.click('[data-start-cooking-button]')
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('[data-recipe-title]')
}

export const waitForFavoriteState = async () => {
    await global.page.waitForSelector('[data-recipe-favorited]')
}

export const getIngredients = async () => {
  return await global.page.$$eval(
    '[data-ingredient-container] [data-name]',
    elements => elements.map(el => el.textContent).sort(),
  )
}

export const getSteps = async () => {
  return await global.page.$$eval(
    '[data-step]',
    elements => elements.map(el => el.textContent),
  )
}

export const editNotes = async (notes) => {
  await global.page.click('.notes-container__notes [data-display-value]')
  await fillInput('.notes-container__notes [data-edit-value]', notes)
  await global.page.click('.notes-container__notes .notes-container__update-value-button')
  await waitForTextByCss('.notes-container__notes [data-display-value]', notes)
}

export const getNotes = async () => {
  await global.page.waitForSelector('.notes-container__notes [data-display-value]')
  return await global.page.$eval('.notes-container__notes [data-display-value]', element => element.textContent)
}

export const getStepNote = async () => {
  const stepNoteContentClassName = '[data-step-note] [data-display-value]'

  await global.page.waitForSelector(stepNoteContentClassName)
  return await global.page.$eval(stepNoteContentClassName, element => element.textContent)
}

export const editStepNote = async (note) => {
  const stepNoteContentClassName = '[data-step-note] [data-display-value]'
  await global.page.click(stepNoteContentClassName)
  await fillInput('[data-step-note] [data-edit-value]', note)
  await global.page.click('[data-step-note] .notes-container__update-value-button')
  await waitForTextByCss(stepNoteContentClassName, note)
}

export const getConfirmationDeleteRecipe = async () => {
  return getTextByCssSelector('[data-confirm-delete-title]')
}

export const clickOnConfirmDeleteButton = async () => {
  await global.page.click('[data-confirm-delete-button]')
}

export const editIngredient = async (index, { name }) => {
  await global.page.click(`.ingredient_${index}`)
  await fillInput(`.ingredient_${index} [data-edit-name]`, name)
  await global.page.click(`.ingredient_${index} [data-edit-save]`)
  await waitForTextByCss(`.ingredient_${index} [data-name]`, name)
}

export const deleteIngredient = async index => {
  await global.page.click(`.ingredient_${index}`)
  await global.page.click(`.ingredient_${index} [data-edit-delete]`)
  await waitForTextNotEmptyByCss('[data-confirm-delete-title]')
  await global.page.click('[data-edit-confirm-delete]')
  await page.waitForResponse(response => response.url().includes('/api/recipes/') && response.status() === 200);
}

export const getIngredient = async index => {
  const name = await getTextByCssSelector(`.ingredient_${index} [data-name]`)
  const quantity = await getTextByCssSelector(`.ingredient_${index} [data-quantity]`)
  return { name, quantity }
}

export const getPortions = async () => {
  return await getTextByCssSelector('[data-portions-value]')
}

export const editPortion = async portions => {
  await global.page.click('[data-portions-value]')
  await fillInput('[data-edit-portions]', portions)
  await global.page.click('[data-save-portions]')
  await waitForTextByCss('[data-portions-value]', portions)
}