import { page } from '../setupE2eTests'

export const waitForPageLoaded = async () => {
  await page.waitForSelector('div.modify-form__name')
}