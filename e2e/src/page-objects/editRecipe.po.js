import { AppUrl, page } from '../setupE2eTests'

export const goTo = async (id) => {
  await page.goto(`${AppUrl}/recipes/${id}/edit`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await page.waitForSelector('div.modify-form__name')
}

export const submitEditedRecipe = async () => {
  await page.click('.modify-form__submit-button')
}

export const fillInForm = async editedRecipe => {
  editedRecipe.name && await fillInput('.modify-form__name input', editedRecipe.name)
  editedRecipe.url && await fillInput('.modify-form__url input', editedRecipe.url)
  editedRecipe.imgUrl && await fillInput('.modify-form__imgUrl input', editedRecipe.imgUrl)

  for (let i = 0; i < editedRecipe.ingredients.length; i++) {
    const ingredient = editedRecipe.ingredients[i]
    ingredient.name && await fillInput(`.modify-form__ingredient-name-${i} input`, ingredient.name)
    ingredient.quantity && await fillInput(`.modify-form__ingredient-quantity-${i} input`, ingredient.quantity)
    ingredient.unit && await fillInput(`.modify-form__ingredient-unit-${i} input`, ingredient.unit)
  }

  for (let i = 0; i < editedRecipe.steps.length; i++) {
    const step = editedRecipe.steps[i]
    step.name && await fillInput(`.modify-form__step-name-${i} input`, step.name)
  }
}

const fillInput = async (selector, value) => {
  await page.click(selector, { clickCount: 3 })
  await page.keyboard.press('Backspace')
  await page.type(selector, `${value}`)
}
