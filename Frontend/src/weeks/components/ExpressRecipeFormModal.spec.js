import { shallow } from 'enzyme/build'
import { ExpressRecipeFormModal } from './ExpressRecipeFormModal'
import React from 'react'

describe('ExpressRecipeFormModal', () => {
  it('calls onRecipeNameChange when changing recipe name', () => {
    const onRecipeNameChangeSpy = jest.fn()
    const recipeListModal = shallow(<ExpressRecipeFormModal onRecipeNameChange={onRecipeNameChangeSpy} />)
    const recipeName = 'Lasagna'

    recipeListModal.find('[data-express-recipe-form-recipe-name]').simulate('change', { target: { value: recipeName } })

    expect(onRecipeNameChangeSpy).toHaveBeenCalledWith(recipeName)
  })

  it('calls onAddRecipeClick when clicking on add recipe button', () => {
    const onAddRecipeClickSpy = jest.fn()
    const recipeName = 'Lasagna'
    const recipeListModal = shallow(
      <ExpressRecipeFormModal
        onAddRecipeClick={onAddRecipeClickSpy}
        recipeName={recipeName} />,
    )

    recipeListModal.find('[data-express-recipe-form-submit-button]').simulate('click')

    expect(onAddRecipeClickSpy).toHaveBeenCalled()
  })
})