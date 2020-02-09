import * as LearningPage from '../page-objects/learning.po'

describe('Learning', () => {
  it('Displays a list of ingredients to validate', async () => {
    await LearningPage.goTo()
    const ingredientsToValidate = await LearningPage.getIngredientsToValidate()
    const expectedIngredients = [{
      line: '2 teaspoons Salt',
      quantity: '2',
      unit: 'tsp',
      name: 'salt',
      language: 'English',
    },{
      line: '350cl de Lait',
      quantity: '350',
      unit: 'cl',
      name: 'lait',
      language: 'French',
    }]

    expect(ingredientsToValidate).toEqual(expectedIngredients)
  })
})