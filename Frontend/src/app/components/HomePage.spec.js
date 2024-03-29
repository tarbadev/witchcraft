import React from 'react'
import { mount } from 'enzyme'

import { DashboardPageContainer } from './DashboardPage'
import * as WeekActions from '../../weeks/actions/WeekActions'
import { getFavoriteRecipes, getLatestRecipes } from '../../recipes/actions/RecipesActions'
import { mockAppContext } from '../../testUtils'

describe('HomePageContainer', function () {
  it('renders without crashing', () => {
    const context = mockAppContext()
    const year = 2019
    const week = 34

    jest
      .spyOn(WeekActions, 'getCurrentWeek')
      .mockImplementation(() => ({ year, week }))

    const home = mount(<DashboardPageContainer />)

    expect(home).toBeDefined()
    expect(context.dispatch).toHaveBeenNthCalledWith(1, getFavoriteRecipes(expect.any(Function)))
    expect(context.dispatch).toHaveBeenNthCalledWith(2, getLatestRecipes(expect.any(Function)))
    expect(context.dispatch).toHaveBeenNthCalledWith(3, WeekActions.getWeek(year, week, expect.any(Function)))
  })
})
