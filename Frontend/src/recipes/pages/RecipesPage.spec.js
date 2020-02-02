import React from 'react'
import { mount } from 'enzyme'

import { RecipesPageContainer } from './RecipesPage'
import { mockAppContext } from 'src/testUtils'
import * as RecipesActions from '../actions/RecipesActions'
import { BrowserRouter } from 'react-router-dom'
import { Header } from 'src/app/components/Header'

describe('RecipesPageContainer', () => {
  it('calls searchRecipe callback on search input change', () => {
    mockAppContext()
    const search = 'test'
    const allRecipes = [
      { id: 1, imgUrl: 'fakeToRemoveWarning', name: 'Recipe test' },
      { id: 2, imgUrl: 'fakeToRemoveWarning', name: 'Recipe Two' },
      { id: 3, imgUrl: 'fakeToRemoveWarning', name: 'Recipe Three' },
    ]

    jest
      .spyOn(RecipesActions, 'getAllRecipes')
      .mockImplementation(onSuccess => onSuccess({ recipes: allRecipes }))

    jest
      .spyOn(RecipesActions, 'filterRecipes')
      .mockImplementation(() => [allRecipes[0]])

    const recipesPageContainer = mount(<BrowserRouter><RecipesPageContainer /></BrowserRouter>)

    expect(recipesPageContainer.find('RecipeList').props().recipes).toHaveLength(3)

    recipesPageContainer.find('.recipes__search-input input').simulate('change', {
      target: {
        value: search,
      },
    })

    expect(recipesPageContainer.find('RecipeList').props().recipes).toHaveLength(1)
  })

  it('loads the recipes when mounting component', () => {
    const context = mockAppContext()

    mount(<RecipesPageContainer />)

    expect(context.dispatch).toHaveBeenCalledWith(RecipesActions.getAllRecipes(expect.any(Function)))
  })

  it('redirects to new recipe page on new recipe button click', () => {
    let context = mockAppContext()
    const pushSpy = jest.fn()

    mount(<RecipesPageContainer history={{ push: pushSpy }} />)
    const headerConfig = context.setHeaderConfig.mock.calls[0][0]
    mockAppContext({ headerConfig })

    const recipesPageContainer = mount(<BrowserRouter>
      <Header />
      <RecipesPageContainer history={{ push: pushSpy }} />
    </BrowserRouter>)

    recipesPageContainer.find('[data-new-recipe]').at(0).simulate('click')

    expect(pushSpy).toHaveBeenCalledWith('/recipes/new')
  })
})
