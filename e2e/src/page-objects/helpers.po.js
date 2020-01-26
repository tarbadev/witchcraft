export const fillInput = async (selector, value) => {
  await global.page.click(selector, { clickCount: 3 })
  await global.page.keyboard.press('Backspace')
  await global.page.type(selector, `${value}`)
}

export const waitForTextByCss = async (cssSelector, text) => {
  await global.page.waitForFunction(
    (selector, textToSearch) => {
      const element = document.querySelector(selector)
      if (element && element.innerText) {
        return element.innerText.toLowerCase().includes(textToSearch)
      }
    },
    {},
    cssSelector.replace(/"/g, '\\"'),
    text.toLowerCase(),
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