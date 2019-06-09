import { shallow } from 'enzyme/build'
import { RecipeListModal } from './RecipeListModal'
import React from 'react'

describe('RecipeListModal', () => {
  it('calls displayExpressRecipeForm when clicking on add express recipe', () => {
    const displayExpressRecipeFormSpy = jest.fn()
    const recipeListModal = shallow(<RecipeListModal displayExpressRecipeForm={displayExpressRecipeFormSpy} />)

    recipeListModal.find('.week-page__add-express-recipe__button').simulate('click')

    expect(displayExpressRecipeFormSpy).toHaveBeenCalled()
  })
})