import * as LearningPage from '../page-objects/learning.po'

describe('Learning', () => {
  it('Displays a list of ingredients to validate', async () => {
    await LearningPage.goTo()
    const ingredientsToValidate = await LearningPage.getIngredients()
    const expectedIngredients = [{
      line: '2 teaspoons Salt',
      quantity: '2',
      unit: 'tsp',
      name: 'salt',
      detail: 'to taste',
      language: 'English',
    },{
      line: '350cl de Lait',
      quantity: '350',
      unit: 'cl',
      name: 'lait',
      detail: '',
      language: 'French',
    }]

    expect(ingredientsToValidate).toEqual(expectedIngredients)
  })

  it('Moves the ingredient to the validated list', async () => {
    await LearningPage.goTo()

    expect(await LearningPage.getIngredients()).toHaveLength(2)

    await LearningPage.validateFirstIngredient()
    expect(await LearningPage.getIngredients()).toHaveLength(1)

    await LearningPage.switchToValidatedTab()
    const ingredientsToValidate = await LearningPage.getIngredients()
    const expectedIngredients = [{
      line: '2 teaspoons Salt',
      quantity: '2',
      unit: 'tsp',
      name: 'salt',
      detail: 'to taste',
      language: 'English',
    },{
      line: '12 oz Boneless Skinless Chicken Breast',
      quantity: '12',
      unit: 'oz',
      name: 'chicken breast',
      detail: 'boneless skinless',
      language: 'English',
    }]

    expect(ingredientsToValidate).toEqual(expectedIngredients)
  })
})