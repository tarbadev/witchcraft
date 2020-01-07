export const fillInput = async (selector, value) => {
  await global.page.click(selector, { clickCount: 3 })
  await global.page.keyboard.press('Backspace')
  await global.page.type(selector, `${value}`)
}

export const waitForTextByCss = async (cssSelector, text) => {
  const selector = cssSelector.replace(/"/g, '\\"')
  const textToSearch = text.toLowerCase()
  await global.page.waitForFunction(
    `document.querySelector("${selector}").innerText.toLowerCase().includes("${textToSearch}")`,
  )
}

export const waitForTextNotEmptyByCss = async (cssSelector) => {
  await global.page.waitForFunction(
    `document.querySelector("${cssSelector.replace(/"/g, '\\"')}").innerText`,
  )
}

export const goToUrl = async (url) => {
  await global.page.goto(`${global.appUrl}${url}`, { waitUntil: 'networkidle2' })
}

export const getTextByCssSelector = async selector => {
  return await global.page.$eval(
    selector,
    element => element.textContent,
  )
}