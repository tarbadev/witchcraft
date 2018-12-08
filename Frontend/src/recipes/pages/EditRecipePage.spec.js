import React from 'react'
import { shallow } from 'enzyme'

import { EditRecipePage } from './EditRecipePage'

import promisedRecipeList from 'test-resources/recipeList.json'

describe('EditRecipePage', () => {
  it('calls the callback when input value changes', () => {
    const changeFormInputSpy = jest.fn()
    const editRecipe = shallow(<EditRecipePage changeFormInput={changeFormInputSpy} form={promisedRecipeList.recipes[0]} />)
    const url = 'fakeUrl'
    const name = 'name'
    const imgUrl = 'imgUrl'

    editRecipe.find('.modify-form__name').simulate('change', { target: { value: name } })
    editRecipe.find('.modify-form__url').simulate('change', { target: { value: url } })
    editRecipe.find('.modify-form__imgUrl').simulate('change', { target: { value: imgUrl } })
    editRecipe.find('.modify-form__ingredient-quantity-0').simulate('change', { target: { value: 12 } })
    editRecipe.find('.modify-form__ingredient-unit-0').simulate('change', { target: { value: 'tbsp' } })
    editRecipe.find('.modify-form__ingredient-name-0').simulate('change', { target: { value: 'Sugar' } })
    editRecipe.find('.modify-form__ingredient-quantity-3').simulate('change', { target: { value: 5 } })
    editRecipe.find('.modify-form__ingredient-unit-3').simulate('change', { target: { value: 'cup' } })
    editRecipe.find('.modify-form__ingredient-name-3').simulate('change', { target: { value: 'All Purpose Flour' } })
    editRecipe.find('.modify-form__step-name-0').simulate('change', { target: { value: 'Add Sugar' } })
    editRecipe.find('.modify-form__step-name-3').simulate('change', { target: { value: 'Mix with flour' } })

    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.name', name)
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.url', url)
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.imgUrl', imgUrl)
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.ingredients.0.quantity', 12)
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.ingredients.0.unit', 'tbsp')
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.ingredients.0.name', 'Sugar')
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.ingredients.3.quantity', 5)
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.ingredients.3.unit', 'cup')
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.ingredients.3.name', 'All Purpose Flour')
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.steps.0.name', 'Add Sugar')
    expect(changeFormInputSpy).toHaveBeenCalledWith('editRecipe.form.steps.3.name', 'Mix with flour')
  })

  it('calls the callback when submit button is clicked on modify-form url', () => {
    const submitFormSpy = jest.fn()
    const form = {
      id: 12,
      name: 'Mini Goat Cheese Stuffed Potato Appetizers',
      url: 'http://example.com/recipe/32434',
      imgUrl: 'http://example.com/recipe/32434.png',
      ingredients: [{
        name: 'Ingredient 1',
        unit: 'tbsp',
        quantity: 12,
      }, {
        name: 'Ingredient 2',
        unit: 'cup',
        quantity: 4,
      }],
      steps: [{
        unit: 'Add sugar',
      }, {
        name: 'Mix with flour',
      }],
    }
    const editRecipe = shallow(<EditRecipePage submitForm={submitFormSpy} form={form} />)

    editRecipe.find('.modify-form__submit-button').simulate('click')

    expect(submitFormSpy).toHaveBeenCalledWith(form)
  })
})
