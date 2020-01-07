import React from 'react'
import { mount } from 'enzyme'

import { IngredientContainer } from 'src/recipes/components/Ingredient'
import { mockAppContext } from 'src/testUtils'
import * as RecipeActions from 'src/recipes/actions/RecipeActions'

describe('Ingredient', () => {
  const index = 0
  const recipeId = 34
  const ingredient = {
    name: 'shredded mozzarella cheese, divided',
    quantity: 4.0,
    unit: 'tbsp',
  }

  it('displays the ingredient editable mode on click', () => {
    mockAppContext()
    const oldIngredientName = 'shredded mozzarella cheese, divided'

    const ingredientContainer = mount(<IngredientContainer
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-name]`)).toHaveLength(0)
    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-save]`)).toHaveLength(0)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')

    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-name] input`)).toHaveLength(1)
    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-name] input`).at(0).prop('value'))
      .toEqual(oldIngredientName)
    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-save] button`)).toHaveLength(1)
  })

  it('Updates the ingredient name on change', () => {
    mockAppContext()
    const oldIngredientName = 'shredded mozzarella cheese, divided'
    const newIngredientName = 'shredded mozzarella cheese'

    const ingredientContainer = mount(<IngredientContainer
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-name] input`).at(0).prop('value'))
      .toEqual(oldIngredientName)

    ingredientContainer.find(`.ingredient_${index} [data-edit-name] input`)
      .at(0)
      .simulate('change', { target: { value: newIngredientName } })

    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-name] input`).at(0).prop('value'))
      .toEqual(newIngredientName)
  })

  it('Hides the editable ingredient on click away', () => {
    mockAppContext()

    const ingredientContainer = mount(<IngredientContainer
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-name] input`)).toHaveLength(1)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('blur')
    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-name]`)).toHaveLength(0)
  })

  it('Dispatches an update ingredient call', () => {
    const context = mockAppContext()

    const ingredientContainer = mount(<IngredientContainer
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
    ingredientContainer.find(`.ingredient_${index} [data-edit-save] button`).simulate('mousedown')

    expect(context.dispatch)
      .toHaveBeenLastCalledWith(RecipeActions.updateIngredient(recipeId, ingredient, expect.any(Function)))
  })

  it('Updates the ingredient when an update ingredient call is successful', () => {
    mockAppContext()
    const newIngredient = {
      name: 'shredded mozzarella cheese',
      quantity: 2.0,
      unit: 'oz',
    }

    jest
      .spyOn(RecipeActions, 'updateIngredient')
      .mockImplementation((id, ingredient, onSuccess) => onSuccess(newIngredient))

    const ingredientContainer = mount(<IngredientContainer
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    const expectIngredientDisplayed = (expectedIngredient) => {
      expect(ingredientContainer.find(`.ingredient_${index} [data-name]`).at(0).text()).toEqual(expectedIngredient.name)
      expect(ingredientContainer.find(`.ingredient_${index} [data-quantity]`).at(0).text())
        .toEqual(expectedIngredient.quantity.toString())
      expect(ingredientContainer.find(`.ingredient_${index} [data-unit]`).at(0).text()).toEqual(expectedIngredient.unit)
    }

    expectIngredientDisplayed(ingredient)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
    ingredientContainer.find(`.ingredient_${index} [data-edit-save] button`).simulate('mousedown')
    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('blur')

    expectIngredientDisplayed(newIngredient)
  })
})