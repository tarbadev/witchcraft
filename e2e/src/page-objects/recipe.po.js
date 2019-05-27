import { fillInput, goToUrl } from './helpers.po'

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
  await global.page.waitForSelector('h1.title')
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
    'div.paper p[data-step]',
    elements => elements.map(el => el.textContent),
  )
}

export const editNotes = async (notes) => {
  await global.page.click('.notes')
  await fillInput('.editableNotes', notes)
  await global.page.click('.updateNotesButton')
  await global.page.click('.updateNotesButton')
}

export const getNotes = async () => {
  await global.page.waitForSelector('.notes')
  return await global.page.$eval('.notes', element => element.textContent)
}
