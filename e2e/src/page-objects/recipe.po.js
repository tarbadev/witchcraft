import { AppUrl, page } from '../setupE2eTests'

export const isFavorite = async () => {
  const favoriteButton = await page.$('.favorite')
  return favoriteButton !== null
}

export const clickOnFavoriteButton = async () => {
  await page.click('.favoriteButton')
}

export const goTo = async (id) => {
  await page.goto(`${AppUrl}/recipes/${id}`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await page.waitForSelector('h1.title')
}

export const waitForFavoriteState = async () => {
  await page.waitForSelector('.favorite')
}
