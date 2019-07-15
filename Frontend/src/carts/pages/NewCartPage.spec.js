import React from 'react'
import { mount } from 'enzyme'

import { NewCartPageContainer } from './NewCartPage'
import { mockAppContext } from 'src/testUtils'
import * as RecipesActions from 'src/recipes/actions/RecipesActions'
import * as NewCartActions from '../actions/NewCartActions'

describe('NewCartPageContainer', () => {
  it('loads the recipes when mounting the component', () => {
    const context = mockAppContext()

    mount(<NewCartPageContainer />)

    expect(context.dispatch).toHaveBeenCalledWith(RecipesActions.getAllRecipes(expect.any(Function)))
  })

  it('dispatches createCart when clicking on the generate cart button', () => {
    const context = mockAppContext()
    const recipes = [
      { id: 1, name: 'Recipe 1' },
      { id: 2, name: 'Recipe 2' },
      { id: 3, name: 'Recipe 3' },
      { id: 4, name: 'Recipe 4' },
    ]
    const recipeIds = [1, 3]

    jest
      .spyOn(RecipesActions, 'getAllRecipes')
      .mockImplementation(onSuccess => onSuccess({ recipes: recipes }))

    const newCartPage = mount(<NewCartPageContainer />)

    newCartPage.find('.new-cart-page__switch-recipe-0 input').simulate('change')
    newCartPage.find('.new-cart-page__switch-recipe-2 input').simulate('change')

    newCartPage.find('.new-cart-page__generate-button button').simulate('click')

    expect(context.dispatch).toHaveBeenCalledWith(NewCartActions.createCart(recipeIds, expect.any(Function)))
  })

  it('redirects to new cart page when cart is saved', () => {
    mockAppContext()
    const pushSpy = jest.fn()
    const recipes = [
      { id: 1, name: 'Recipe 1' },
      { id: 2, name: 'Recipe 2' },
      { id: 3, name: 'Recipe 3' },
      { id: 4, name: 'Recipe 4' },
    ]

    jest
      .spyOn(RecipesActions, 'getAllRecipes')
      .mockImplementation(onSuccess => onSuccess({ recipes: recipes }))

    jest
      .spyOn(NewCartActions, 'createCart')
      .mockImplementation((list, onSuccess) => onSuccess({ id: 4 }))

    const newCartPage = mount(<NewCartPageContainer history={{ push: pushSpy }} />)

    newCartPage.find('.new-cart-page__switch-recipe-0 input').simulate('change')
    newCartPage.find('.new-cart-page__switch-recipe-2 input').simulate('change')

    newCartPage.find('.new-cart-page__generate-button button').simulate('click')

    expect(pushSpy).toHaveBeenCalledWith('/carts/4')
  })
})
