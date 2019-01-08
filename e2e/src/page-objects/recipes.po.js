import { AppUrl, page } from '../setupE2eTests'

export const goTo = async () => {
  await page.goto(`${AppUrl}/recipes`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await page.waitForSelector('.recipes__search-input')
}

export const search = async keywords => {
  await page.type('.recipes__search-input input', keywords)
}

export const getRecipes = async () => {
  return await page.$$eval(
    '.recipe-list__link',
    elements => elements.map(el => el.textContent),
  )
}
