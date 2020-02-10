import React from 'react'
import { mount } from 'enzyme'
import { LearningPage } from 'src/learnings/pages/LearningPage'
import { mockAppContext } from 'src/testUtils'

import * as LearningActions from 'src/learnings/actions/LearningActions'

describe('LearningPage', () => {
  it('Loads the ingredients when mounting', () => {
    const context = mockAppContext()

    const learningPage = mount(<LearningPage />)

    expect(context.dispatch).toHaveBeenCalledWith(LearningActions.getIngredientsToValidate(expect.any(Function)))

    expect(learningPage.find('[data-ingredient]')).toHaveLength(0)
  })

  it('Displays the ingredients when they are fetched and not valid', () => {
    mockAppContext()

    const ingredientsToValidate = [{
      id: 12,
      line: 'Some ingredient line',
      name: 'some ingredient',
      quantity: 2.0,
      unit: 'tsp',
      language: 'ENGLISH',
      valid: false,
    }]

    jest
      .spyOn(LearningActions, 'getIngredientsToValidate')
      .mockImplementation(onSuccess => onSuccess(ingredientsToValidate))

    const learningPage = mount(<LearningPage />)

    expect(learningPage.find('[data-ingredient]').length).toBeGreaterThanOrEqual(1)
  })

  it('Dispatches an action with the validated ingredient', () => {
    const { dispatch } = mockAppContext()

    const ingredientsToValidate = [{
      id: 12,
      line: '2tsp + 2tsp Salt',
      name: '+ 2tsp salt',
      quantity: 2.0,
      unit: 'tsp',
      language: 'ENGLISH',
      valid: false,
    }]
    const newIngredient = {
      name: 'salt',
      quantity: 1.0,
      unit: 'tbsp',
      language: 'ENGLISH',
    }

    jest
      .spyOn(LearningActions, 'getIngredientsToValidate')
      .mockImplementation(onSuccess => onSuccess(ingredientsToValidate))

    const learningPage = mount(<LearningPage />)
    learningPage.find('[data-ingredient-name] input').simulate('change', { target: { value: newIngredient.name } })
    learningPage.find('[data-ingredient-quantity] input')
      .simulate('change', { target: { value: newIngredient.quantity } })
    learningPage.find('[data-ingredient-unit] input').simulate('change', { target: { value: newIngredient.unit } })

    learningPage.find('[data-ingredient-validate] button').simulate('click')

    expect(dispatch).toHaveBeenCalledWith(LearningActions.validateIngredient(12, newIngredient, expect.any(Function), expect.any(Function)))
  })

  it('Reloads the list of learning ingredients when validating one', () => {
    mockAppContext()

    const ingredientsToValidate = [{
      id: 12,
      line: '2tsp + 2tsp Salt',
      name: '+ 2tsp salt',
      quantity: 2.0,
      unit: 'tsp',
      language: 'ENGLISH',
      valid: false,
    }]

    const ingredientsValidated = [{
      id: 12,
      line: '2tsp + 2tsp Salt',
      name: '+ 2tsp salt',
      quantity: 2.0,
      unit: 'tsp',
      language: 'ENGLISH',
      valid: true,
    }]

    jest
      .spyOn(LearningActions, 'getIngredientsToValidate')
      .mockImplementation(onSuccess => onSuccess(ingredientsToValidate))

    jest
      .spyOn(LearningActions, 'validateIngredient')
      .mockImplementation((id, newIngredient, onSuccess) => onSuccess())

    const learningPage = mount(<LearningPage />)

    jest
      .spyOn(LearningActions, 'getIngredientsToValidate')
      .mockImplementation(onSuccess => onSuccess(ingredientsValidated))

    expect(learningPage.find('[data-ingredient]').length).toBeGreaterThanOrEqual(1)

    learningPage.find('[data-ingredient-validate] button').simulate('click')

    expect(learningPage.find('[data-ingredient]').length).toEqual(0)
  })
})