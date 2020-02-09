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

  it('Displays the ingredients when they are fetched', () => {
    mockAppContext()

    const ingredientsToValidate = [{
      id: 12,
      line: 'Some ingredient line',
      name: 'some ingredient',
      quantity: 2.0,
      unit: 'tsp',
      language: 'ENGLISH',
      valid: true,
    }]

    jest
      .spyOn(LearningActions, 'getIngredientsToValidate')
      .mockImplementation(onSuccess => onSuccess(ingredientsToValidate))

    const learningPage = mount(<LearningPage />)

    expect(learningPage.find('[data-ingredient]').length).toBeGreaterThanOrEqual(1)
  })
})