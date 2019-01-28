import { AppUrl, page } from '../setupE2eTests'

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
