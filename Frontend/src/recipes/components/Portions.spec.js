import React from 'react'
import { mount } from 'enzyme'

import { PortionsContainer } from 'src/recipes/components/Portions'
import { mockAppContext } from 'src/testUtils'
import * as RecipeActions from 'src/recipes/actions/RecipeActions'

describe('Portions', () => {
  const recipeId = 34
  const portions = '4'

  it('displays the portions editable mode on click', () => {
    mockAppContext()
    const portionsContainer = mount(<PortionsContainer
      portions={portions}
      recipeId={recipeId}
    />)

    expect(portionsContainer.find('[data-edit-portions]')).toHaveLength(0)
    expect(portionsContainer.find('[data-save-portions]')).toHaveLength(0)

    portionsContainer.find('[data-portions-container]').at(0).simulate('click')

    expect(portionsContainer.find('[data-edit-portions] input')).toHaveLength(1)
    expect(portionsContainer.find('[data-edit-portions] input').at(0).prop('value')).toEqual(portions)
    expect(portionsContainer.find('[data-save-portions] button')).toHaveLength(1)
  })

  it('Updates the portions on change', () => {
    mockAppContext()
    const newPortions = '8'

    const portionsContainer = mount(<PortionsContainer
      portions={portions}
      recipeId={recipeId}
    />)

    portionsContainer.find('[data-portions-container]').at(0).simulate('click')
    expect(portionsContainer.find('[data-edit-portions] input').at(0).prop('value')).toEqual(portions)

    portionsContainer.find('[data-edit-portions] input').at(0).simulate('change', { target: { value: newPortions } })

    expect(portionsContainer.find('[data-edit-portions] input').at(0).prop('value')).toEqual(newPortions)
  })

  it('Dispatches an update portions call on save button click', () => {
    const context = mockAppContext()
    const newPortions = '8'

    const portionsContainer = mount(<PortionsContainer
      portions={portions}
      recipeId={recipeId}
    />)

    portionsContainer.find('[data-portions-container]').at(0).simulate('click')
    portionsContainer.find('[data-edit-portions] input').at(0).simulate('change', { target: { value: newPortions } })
    portionsContainer.find('[data-save-portions] button').simulate('mousedown')

    expect(context.dispatch)
      .toHaveBeenLastCalledWith(RecipeActions.updatePortions(recipeId, newPortions, expect.any(Function)))
  })

  it('Updates the portions when an update portions call is successful and hides editable form', () => {
    mockAppContext()
    const onPortionsUpdatedSpy = jest.fn()
    const newPortions = '8'
    const recipe = { portions: newPortions }

    jest
      .spyOn(RecipeActions, 'updatePortions')
      .mockImplementation((id, portions, onSuccess) => onSuccess(recipe))

    const portionsContainer = mount(<PortionsContainer
      portions={portions}
      recipeId={recipeId}
      onPortionsUpdated={onPortionsUpdatedSpy}
    />)

    expect(portionsContainer.find('[data-portions-value]').at(0).text()).toEqual(portions)

    portionsContainer.find('[data-portions-container]').at(0).simulate('click')
    portionsContainer.find('[data-save-portions] button').simulate('mousedown')

    expect(portionsContainer.find('[data-edit-portions] input')).toHaveLength(0)

    expect(onPortionsUpdatedSpy).toHaveBeenCalledWith(recipe)
  })
})