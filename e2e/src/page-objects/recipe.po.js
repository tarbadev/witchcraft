import { AppUrl, page } from '../setupE2eTests'

export const goTo = async (id) => {
  await page.goto(`${AppUrl}/recipes/${id}`)
  await page.waitForSelector('.title')
}

export const waitForPageLoaded = async () => {
  await page.waitForSelector('h1.title')
}
