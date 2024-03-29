import React from 'react'
import { mount } from 'enzyme'
import { RecipeListModalContainer } from './RecipeListModal'
import { mockAppContext } from 'src/testUtils'
import * as RecipesActions from 'src/recipes/actions/RecipesActions'
import { addExpressRecipe } from '../actions/RecipeListModalActions'

describe('RecipeListModalContainer', () => {
  it('loads the recipes when mounting the component', () => {
    const context = mockAppContext()

    mount(<RecipeListModalContainer config={{ isModalOpen: true }} />)

    expect(context.dispatch).toHaveBeenCalledWith(RecipesActions.getAllRecipes(expect.any(Function)))
  })

  it('on express recipe click displays the Express Recipe modal', () => {
    mockAppContext()
    const recipeListModalContainer = mount(<RecipeListModalContainer config={{ isModalOpen: true }} />)

    expect(recipeListModalContainer.find('[data-name="express-recipe-form-modal"]').at(0).props().open).toBeFalsy()

    recipeListModalContainer.find('.week-page__add-express-recipe__button button').simulate('click')

    expect(recipeListModalContainer.find('[data-name="express-recipe-form-modal"]').at(0).props().open).toBeTruthy()
  })

  it('on add express recipe click saves the recipe', () => {
    const context = mockAppContext()
    const setRecipeSpy = jest.fn()
    const day = 'monday'
    const meal = 'lunch'
    const recipe = 'Some new recipe'
    const recipeListModalContainer = mount(<RecipeListModalContainer
      config={{ isModalOpen: true, day, meal }}
      setRecipe={setRecipeSpy}
    />)

    expect(recipeListModalContainer.find('[data-name="express-recipe-form-modal"]').at(0).props().open).toBeFalsy()

    recipeListModalContainer.find('.week-page__add-express-recipe__button button').simulate('click')
    recipeListModalContainer.find('[data-express-recipe-form-recipe-name] input')
      .simulate('change', { target: { value: recipe } })
    recipeListModalContainer.find('[data-express-recipe-form-submit-button] button').simulate('click')

    expect(context.dispatch).toHaveBeenLastCalledWith(addExpressRecipe(recipe, expect.any(Function)))
  })

  it('displays the current recipe with a specific css class', () => {
    mockAppContext()
    const allRecipes = [
      { id: 1, imgUrl: 'fakeToRemoveWarning1', name: 'Recipe test' },
      { id: 2, imgUrl: 'fakeToRemoveWarning2', name: 'Recipe Two' },
      { id: 3, imgUrl: 'fakeToRemoveWarning3', name: 'Recipe Three' },
    ]

    jest
      .spyOn(RecipesActions, 'getAllRecipes')
      .mockImplementation(onSuccess => onSuccess({ recipes: allRecipes }))

    const withoutCurrentRecipe = mount(<RecipeListModalContainer
      config={{ isModalOpen: true, currentRecipeIds: [] }} />)
    expect(withoutCurrentRecipe.find('.current-recipe')).toHaveLength(0)

    const withCurrentRecipe = mount(<RecipeListModalContainer config={{ isModalOpen: true, currentRecipeIds: [3] }} />)
    expect(withCurrentRecipe.find('.current-recipe')).toHaveLength(4)
  })

  it('when clicking on current recipe adds a remove flag', () => {
    mockAppContext()
    const setRecipeSpy = jest.fn()
    const existingRecipe = { id: 4, imgUrl: 'fakeToRemoveWarning1', name: 'Recipe test' }
    const config = { isModalOpen: true, currentRecipeIds: [existingRecipe.id], day: 'monday', meal: 'lunch' }
    const allRecipes = [
      { id: 2, imgUrl: 'fakeToRemoveWarning2', name: 'Recipe Two' },
      { id: 3, imgUrl: 'fakeToRemoveWarning3', name: 'Recipe Three' },
      existingRecipe,
    ]

    jest
      .spyOn(RecipesActions, 'getAllRecipes')
      .mockImplementation(onSuccess => onSuccess({ recipes: allRecipes }))

    const recipeListModalContainer = mount(<RecipeListModalContainer config={config} setRecipe={setRecipeSpy} />)
    expect(recipeListModalContainer.find('.current-recipe')).toHaveLength(4)

    recipeListModalContainer.find('div.recipe-card').at(2).simulate('click')

    expect(setRecipeSpy).toHaveBeenCalledWith({ ...existingRecipe, remove: true }, config.day, config.meal)
  })
})