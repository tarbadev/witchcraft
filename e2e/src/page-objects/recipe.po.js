import { fillInput, getTextByCssSelector, goToUrl, waitForTextByCss } from './helpers.po'

export const goTo = async (id) => {
  await goToUrl(`/recipes/${id}`)
  await waitForPageLoaded()
}

export const isFavorite = async () => {
  const favoriteButton = await global.page.$('.favorite')
  return favoriteButton !== null
}

export const clickOnFavoriteButton = async () => {
  await global.page.click('.favoriteButton')
}

export const clickOnModifyButton = async () => {
  await global.page.click('.modifyButton')
}

export const clickOnDeleteButton = async () => {
  await global.page.click('.deleteButton')
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('h5.title')
}

export const waitForFavoriteState = async () => {
  await global.page.waitForSelector('.favorite')
}

export const getIngredients = async () => {
  return await global.page.$$eval(
    'div.paper p.ingredient',
    elements => elements.map(el => el.textContent),
  )
}

export const getSteps = async () => {
  return await global.page.$$eval(
    'p[data-step]',
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

export const getPortions = () => {
  return getTextByCssSelector('.portions-value')
}

export const displayStepNote = async number => {
  const stepNoteIconClassName = `.step-note-${number}`

  await global.page.waitForSelector(stepNoteIconClassName)
  await global.page.click(stepNoteIconClassName)
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
