import { goToUrl } from './helpers.po'

export const goTo = async () => {
  await goToUrl(`/carts`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('[data-new-cart-button]')
}

export const getCarts = async () => {
  return await global.page.$$eval(
    '.cart-list__item',
    elements => elements.map(el => el.textContent),
  )
}

export const newCart = async () => {
  await global.page.click('[data-open-menu]')
  await global.page.click('[data-new-cart-button]')
}