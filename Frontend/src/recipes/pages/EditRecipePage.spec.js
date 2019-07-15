import React from 'react'
import { mount } from 'enzyme'

import { EditRecipePage, EditRecipePageContainer } from './EditRecipePage'

import promisedRecipeList from 'test-resources/recipeList.json'
import { initialState } from 'src/RootReducer'
import * as StoreProvider from 'src/StoreProvider'
import { getRecipe } from '../actions/RecipeActions'

describe('EditRecipePage', () => {
  it('calls the callback when input value changes', () => {
    const submitFormSpy = jest.fn()
    const editRecipe = mount(<EditRecipePage submitForm={submitFormSpy} recipe={promisedRecipeList.recipes[0]} />)
    const url = 'fakeUrl'
    const name = 'name'
    const imgUrl = 'imgUrl'
    const ingredientQuantity0 = 12
    const ingredientUnit0 = 'tbsp'
    const ingredientName0 = 'Sugar'
    const ingredientQuantity3 = 5
    const ingredientUnit3 = 'cup'
    const ingredientName3 = 'All Purpose Flour'
    const step0 = 'Add Sugar'
    const step3 = 'Mix with flour'

    editRecipe.find('.modify-form__name input').simulate('change', { target: { value: name } })
    editRecipe.find('.modify-form__url input').simulate('change', { target: { value: url } })
    editRecipe.find('.modify-form__imgUrl input').simulate('change', { target: { value: imgUrl } })
    editRecipe.find('.modify-form__ingredient-quantity-0 input').simulate('change', { target: { value: ingredientQuantity0 } })
    editRecipe.find('.modify-form__ingredient-unit-0 input').simulate('change', { target: { value: ingredientUnit0 } })
    editRecipe.find('.modify-form__ingredient-name-0 input').simulate('change', { target: { value: ingredientName0 } })
    editRecipe.find('.modify-form__ingredient-quantity-3 input').simulate('change', { target: { value: ingredientQuantity3 } })
    editRecipe.find('.modify-form__ingredient-unit-3 input').simulate('change', { target: { value: ingredientUnit3 } })
    editRecipe.find('.modify-form__ingredient-name-3 input').simulate('change', { target: { value: ingredientName3 } })
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
    form.ingredients[0] = {
      ...promisedRecipeList.recipes[0].ingredients[0],
      unit: ingredientUnit0,
      name: ingredientName0,
      quantity: ingredientQuantity0,
    }
    form.ingredients[3] = {
      ...promisedRecipeList.recipes[0].ingredients[3],
      unit: ingredientUnit3,
      name: ingredientName3,
      quantity: ingredientQuantity3,
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
    const context = { state: initialState, dispatch: jest.fn() }
    const id = 45

    jest
      .spyOn(StoreProvider, 'useAppContext')
      .mockImplementation(() => context)

    mount(<EditRecipePageContainer match={{ params: { id } }} />)

    expect(context.dispatch).toHaveBeenNthCalledWith(1, getRecipe(id, expect.any(Function), expect.any(Function)))
  })
})
