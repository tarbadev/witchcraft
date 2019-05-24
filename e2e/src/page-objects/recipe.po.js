import { AppUrl, page } from '../setupE2eTests'
import { fillInput } from './helpers.po'

export const goTo = async (id) => {
  await page.goto(`${AppUrl}/recipes/${id}`)
  await waitForPageLoaded()
}

export const isFavorite = async () => {
  const favoriteButton = await page.$('.favorite')
  return favoriteButton !== null
}

export const clickOnFavoriteButton = async () => {
  await page.click('.favoriteButton')
}

export const clickOnModifyButton = async () => {
  await page.click('.modifyButton')
}

export const clickOnDeleteButton = async () => {
  await page.click('.deleteButton')
}

export const waitForPageLoaded = async () => {
  await page.waitForSelector('h1.title')
}

export const waitForFavoriteState = async () => {
  await page.waitForSelector('.favorite')
}

export const getIngredients = async () => {
  return await page.$$eval(
    'div.paper p.ingredient',
    elements => elements.map(el => el.textContent),
  )
}

export const getSteps = async () => {
  return await page.$$eval(
    'div.paper p[data-step]',
    elements => elements.map(el => el.textContent),
  )
}

export const editNotes = async (notes) => {
  await page.click('.notes')
  await fillInput('.editableNotes', notes)
  await page.click('.updateNotesButton')
  await page.waitForSelector('.notes')
}

export const getNotes = async () => {
  await page.waitForSelector('.notes')
  return await page.$eval('.notes', element => element.textContent)
}
