export const clickOnRecipe = async recipeName => {
  const splitedQuotes = recipeName.replace(/'/g, '\', "\'", \'')
  const escapedText = `concat('${splitedQuotes}', '')`

  const recipes = await global.page.$x(`//span[contains(@class, 'card-header-title') and text() = ${escapedText}]`)

  if (recipes.length > 0) {
    await recipes[0].click()
  } else {
    throw new Error(`Recipe not found: ${recipeName}`)
  }
}

export const goTo = async () => {
  await global.page.goto(`${appUrl}/recipes`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('.recipe-list__link')
}

export const search = async keywords => {
  await global.page.type('.recipes__search-input input', keywords)
}

export const getRecipes = async () => {
  return await global.page.$$eval(
    '.recipe-list__link',
    elements => elements.map(el => el.textContent),
  )
}
