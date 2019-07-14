import React from 'react'
import { mount } from 'enzyme'

import { HomePage } from './HomePage'
import * as StoreProvider from './StoreProvider'
import * as WeekActions from './weeks/actions/WeekActions'
import { initialState } from './RootReducer'
import { getFavoriteRecipes, getLatestRecipes } from './recipes/actions/RecipesActions'

describe('HomePage', function () {
  it('renders without crashing', () => {
    const context = { state: initialState, dispatch: jest.fn() }
    const year = 2019
    const week = 34

    jest
      .spyOn(StoreProvider, 'useAppContext')
      .mockImplementation(() => context)

    jest
      .spyOn(WeekActions, 'getCurrentWeek')
      .mockImplementation(() => ({ year, week }))

    const home = mount(<HomePage />)

    expect(home).toBeDefined()
    expect(context.dispatch).toHaveBeenNthCalledWith(1, getFavoriteRecipes(expect.any(Function)))
    expect(context.dispatch).toHaveBeenNthCalledWith(2, getLatestRecipes(expect.any(Function)))
    expect(context.dispatch).toHaveBeenNthCalledWith(3, WeekActions.getWeek(year, week, expect.any(Function)))
  })
})
