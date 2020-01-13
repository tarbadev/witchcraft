import { fillInput, goToUrl } from './helpers.po'

export const goTo = async (id) => {
  await goToUrl(`/recipes/${id}/edit`)
  await waitForPageLoaded()
}

export const waitForPageLoaded = async () => {
  await global.page.waitForSelector('div.modify-form__name')
}

export const submitEditedRecipe = async () => {
  await global.page.click('.modify-form__submit-button')
}

export const fillInForm = async editedRecipe => {
  editedRecipe.name && await fillInput('.modify-form__name input', editedRecipe.name)
  editedRecipe.url && await fillInput('.modify-form__url input', editedRecipe.url)
  editedRecipe.imgUrl && await fillInput('.modify-form__imgUrl input', editedRecipe.imgUrl)

  for (let i = 0; i < editedRecipe.steps.length; i++) {
    const step = editedRecipe.steps[i]
    step.name && await fillInput(`.modify-form__step-name-${i} input`, step.name)
  }
}