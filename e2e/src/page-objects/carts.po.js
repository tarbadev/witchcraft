import { goToUrl, waitForTextNotEmptyByCss } from './helpers.po'

export const goTo = async () => {
  await goToUrl(`/carts`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('.cart-page__new-cart-button')
  await waitForTextNotEmptyByCss('.cart-page__new-cart-button')
}

export const getCarts = async () => {
  return await global.page.$$eval(
    '.cart-list__item',
    elements => elements.map(el => el.textContent),
  )
}