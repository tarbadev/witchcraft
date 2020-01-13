import React from 'react'
import { mount } from 'enzyme'

import { EditRecipePage, EditRecipePageContainer } from './EditRecipePage'

import promisedRecipeList from 'test-resources/recipeList.json'
import { getRecipe } from '../actions/RecipeActions'
import { mockAppContext } from 'src/testUtils'

describe('EditRecipePage', () => {
  it('calls the callback when input value changes', () => {
    const submitFormSpy = jest.fn()
    const editRecipe = mount(<EditRecipePage submitForm={submitFormSpy} recipe={promisedRecipeList.recipes[0]} />)
    const url = 'fakeUrl'
    const name = 'name'
    const imgUrl = 'imgUrl'
    const step0 = 'Add Sugar'
    const step3 = 'Mix with flour'

    editRecipe.find('.modify-form__name input').simulate('change', { target: { value: name } })
    editRecipe.find('.modify-form__url input').simulate('change', { target: { value: url } })
    editRecipe.find('.modify-form__imgUrl input').simulate('change', { target: { value: imgUrl } })
    editRecipe.find('.modify-form__step-name-0 input').simulate('change', { target: { value: step0 } })
    editRecipe.find('.modify-form__step-name-3 input').simulate('change', { target: { value: step3 } })

    editRecipe.find('.modify-form__submit-button button').simulate('click')

    const form = {
      ...promisedRecipeList.recipes[0],
      name,
      url,
      imgUrl,
      originUrl: undefined,
      ingredients: [...promisedRecipeList.recipes[0].ingredients],
      steps: [...promisedRecipeList.recipes[0].steps],
    }
    form.steps[0] = {
      ...promisedRecipeList.recipes[0].steps[0],
      name: step0,
    }
    form.steps[3] = {
      ...promisedRecipeList.recipes[0].steps[3],
      name: step3,
    }

    expect(submitFormSpy).toHaveBeenCalledWith(form)
  })

  it('loads the recipe when mounting', () => {
    const context = mockAppContext()
    const id = 45

    mount(<EditRecipePageContainer match={{ params: { id } }} />)

    expect(context.dispatch).toHaveBeenNthCalledWith(1, getRecipe(id, expect.any(Function), expect.any(Function)))
  })
})
