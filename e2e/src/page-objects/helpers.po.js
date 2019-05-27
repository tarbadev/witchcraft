export const fillInput = async (selector, value) => {
  await global.page.click(selector, { clickCount: 3 })
  await global.page.keyboard.press('Backspace')
  await global.page.type(selector, `${value}`)
}