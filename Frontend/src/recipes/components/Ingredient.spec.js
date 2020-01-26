import React from 'react'
import { mount } from 'enzyme'

import { Ingredient } from 'src/recipes/components/Ingredient'
import { mockAppContext } from 'src/testUtils'
import * as RecipeActions from 'src/recipes/actions/RecipeActions'

describe('Ingredient', () => {
  const index = 0
  const recipeId = 34
  const ingredient = {
    id: 12,
    name: 'shredded mozzarella cheese, divided',
    quantity: 4.0,
    unit: 'tbsp',
  }

  it('displays the ingredient editable mode on click', () => {
    mockAppContext()
    const oldIngredientName = 'shredded mozzarella cheese, divided'

    const ingredientContainer = mount(<Ingredient
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

    const ingredientContainer = mount(<Ingredient
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

  it('Updates the ingredient quantity on change', () => {
    mockAppContext()
    const oldIngredientQuantity = 4.0
    const newIngredientQuantity = 10.0

    const ingredientContainer = mount(<Ingredient
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-quantity] input`).at(0).prop('value'))
      .toEqual(oldIngredientQuantity)

    ingredientContainer.find(`.ingredient_${index} [data-edit-quantity] input`)
      .at(0)
      .simulate('change', { target: { value: newIngredientQuantity } })

    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-quantity] input`).at(0).prop('value'))
      .toEqual(newIngredientQuantity)
  })

  it('Updates the ingredient unit on change', () => {
    mockAppContext()
    const oldIngredientUnit = 'tbsp'
    const newIngredientUnit = 'oz'

    const ingredientContainer = mount(<Ingredient
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-unit] input`).at(0).prop('value'))
      .toEqual(oldIngredientUnit)

    ingredientContainer.find(`.ingredient_${index} [data-edit-unit] input`)
      .at(0)
      .simulate('change', { target: { value: newIngredientUnit } })

    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-unit] input`).at(0).prop('value'))
      .toEqual(newIngredientUnit)
  })

  it('Dispatches an update ingredient call on save button click', () => {
    const context = mockAppContext()
    const newIngredient = {
      id: 12,
      name: 'shredded mozzarella cheese',
      quantity: 31.0,
      unit: 'oz',
    }

    const ingredientContainer = mount(<Ingredient
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')

    ingredientContainer.find(`.ingredient_${index} [data-edit-name] input`).at(0)
      .simulate('change', { target: { value: newIngredient.name } })
    ingredientContainer.find(`.ingredient_${index} [data-edit-quantity] input`).at(0)
      .simulate('change', { target: { value: newIngredient.quantity } })
    ingredientContainer.find(`.ingredient_${index} [data-edit-unit] input`).at(0)
      .simulate('change', { target: { value: newIngredient.unit } })

    ingredientContainer.find(`.ingredient_${index} [data-edit-save] button`).simulate('mousedown')

    expect(context.dispatch)
      .toHaveBeenLastCalledWith(RecipeActions.updateIngredient(recipeId, newIngredient, expect.any(Function)))
  })

  it('Updates the ingredient when an update ingredient call is successful and hides editable form', () => {
    mockAppContext()
    const newIngredient = {
      name: 'shredded mozzarella cheese',
      quantity: 2.0,
      unit: 'oz',
    }

    jest
      .spyOn(RecipeActions, 'updateIngredient')
      .mockImplementation((id, ingredient, onSuccess) => onSuccess(newIngredient))

    const ingredientContainer = mount(<Ingredient
      ingredient={ingredient}
      index={index}
      recipeId={recipeId}
    />)

    const expectIngredientDisplayed = (expectedIngredient) => {
      expect(ingredientContainer.find(`.ingredient_${index} [data-name]`).at(0).text()).toEqual(expectedIngredient.name)
      expect(ingredientContainer.find(`.ingredient_${index} [data-quantity]`).at(0).text())
        .toEqual(`${expectedIngredient.quantity} ${expectedIngredient.unit}`)
    }

    expectIngredientDisplayed(ingredient)

    ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
    ingredientContainer.find(`.ingredient_${index} [data-edit-save] button`).simulate('mousedown')

    expect(ingredientContainer.find(`.ingredient_${index} [data-edit-name] input`)).toHaveLength(0)

    expectIngredientDisplayed(newIngredient)
  })

  describe('delete', () => {
    it('displays confirmation dialog when delete button is clicked', () => {
      mockAppContext()

      const ingredientContainer = mount(<Ingredient
        ingredient={ingredient}
        index={index}
        recipeId={recipeId}
      />)

      ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
      expect(ingredientContainer.find('[data-confirm-delete]')).toHaveLength(0)

      ingredientContainer.find(`.ingredient_${index} [data-edit-delete] button`).simulate('click')
      expect(ingredientContainer.find('[data-confirm-delete-title]')).toHaveLength(3)
    })

    it('closes dialog when cancel button clicked', () => {
      mockAppContext()
      const ingredientContainer = mount(<Ingredient
        ingredient={ingredient}
        index={index}
        recipeId={recipeId}
      />)

      expect(ingredientContainer.find('[data-confirm-delete-title]')).toHaveLength(0)

      ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
      ingredientContainer.find(`.ingredient_${index} [data-edit-delete] button`).simulate('click')
      ingredientContainer.find(`.ingredient_${index} [data-edit-cancel-delete] button`).simulate('click')

      setTimeout(done => {
        expect(ingredientContainer.find('[data-confirm-delete-title]')).toHaveLength(0)
        done()
      }, 500)
    })

    it('dispatches deleteIngredient when confirm Delete button clicked', () => {
      const context = mockAppContext()

      const ingredientContainer = mount(<Ingredient
        ingredient={ingredient}
        index={index}
        recipeId={recipeId}
      />)

      ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
      ingredientContainer.find(`.ingredient_${index} [data-edit-delete] button`).simulate('click')
      ingredientContainer.find(`.ingredient_${index} [data-edit-confirm-delete] button`).simulate('click')

      setTimeout(done => {
        expect(ingredientContainer.find('[data-confirm-delete-title]')).toHaveLength(0)
        done()
      }, 500)

      expect(context.dispatch).toHaveBeenLastCalledWith(RecipeActions.deleteIngredient(
        recipeId,
        ingredient.id,
        expect.any(Function),
        expect.any(Function),
      ))
    })

    it('calls deleteIngredientCallback when delete is successful', () => {
      mockAppContext()
      const deleteCallbackSpy = jest.fn()

      jest
        .spyOn(RecipeActions, 'deleteIngredient')
        .mockImplementation((id, ingredientId, onSuccess) => onSuccess())

      const ingredientContainer = mount(<Ingredient
        ingredient={ingredient}
        index={index}
        recipeId={recipeId}
        deleteCallback={deleteCallbackSpy}
      />)

      ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
      ingredientContainer.find(`.ingredient_${index} [data-edit-delete] button`).simulate('click')
      ingredientContainer.find(`.ingredient_${index} [data-edit-confirm-delete] button`).simulate('click')

      expect(deleteCallbackSpy).toHaveBeenCalled()
    })

    it('calls deleteIngredientCallback when delete is unsuccessful', () => {
      mockAppContext()
      const deleteCallbackSpy = jest.fn()

      jest
        .spyOn(RecipeActions, 'deleteIngredient')
        .mockImplementation((id, ingredientId, onSuccess, onError) => onError())

      const ingredientContainer = mount(<Ingredient
        ingredient={ingredient}
        index={index}
        recipeId={recipeId}
        deleteCallback={deleteCallbackSpy}
      />)

      ingredientContainer.find(`.ingredient_${index} [data-ingredient-container]`).at(0).simulate('click')
      ingredientContainer.find(`.ingredient_${index} [data-edit-delete] button`).simulate('click')
      ingredientContainer.find(`.ingredient_${index} [data-edit-confirm-delete] button`).simulate('click')

      expect(deleteCallbackSpy).toHaveBeenCalled()
    })
  })
})