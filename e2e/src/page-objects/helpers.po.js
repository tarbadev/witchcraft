import { page } from '../setupE2eTests'

export const fillInput = async (selector, value) => {
  await page.click(selector, { clickCount: 3 })
  await page.keyboard.press('Backspace')
  await page.type(selector, `${value}`)
}