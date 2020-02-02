import React from 'react'

import { WeekPage, WEEKS_IN_A_YEAR } from './WeekPage'
import { mockAppContext } from 'src/testUtils'
import * as WeekActions from 'src/weeks/actions/WeekActions'
import * as NewCartActions from 'src/carts/actions/NewCartActions'
import * as RecipesActions from 'src/recipes/actions/RecipesActions'
import { saveWeek } from '../actions/WeekPageActions'
import { mount } from 'enzyme'
import { BrowserRouter } from 'react-router-dom'
import { Header } from 'src/app/components/Header'

describe('WeekPage', () => {
  const history = {
    location: { search: '' },
    push: jest.fn(),
  }
  const year = '2019'
  const weekNumber = '34'
  const currentWeek = {
    year: Number(year),
    weekNumber: Number(weekNumber),
    days: [
      {
        name: 'MONDAY',
        lunch: [{ id: 1, imgUrl: 'dummy' }],
        diner: [{ id: 3, imgUrl: 'dummy' }],
      },
      {
        name: 'TUESDAY',
        lunch: [],
        diner: [],
      },
    ],
  }

  it('loads the week when mounting the component', () => {
    const context = mockAppContext()

    mount(<WeekPage weekNumber={weekNumber} year={year} history={history} />)

    expect(context.dispatch).toHaveBeenCalledWith(WeekActions.getWeek(year, weekNumber, expect.any(Function)))
  })

  describe('with Header', () => {
    let weekPageContainer
    let context = mockAppContext()

    beforeEach(() => {
      context = mockAppContext()

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

      mount(<WeekPage weekNumber={weekNumber} year={year} history={history} />)

      const headerConfig = context.setHeaderConfig.mock.calls[1][0]
      mockAppContext({ headerConfig })

      weekPageContainer = mount(<BrowserRouter>
        <Header />
        <WeekPage weekNumber={weekNumber} year={year} history={history} />
      </BrowserRouter>)
    })

    it('calls createCartFromWeek on create cart button click', () => {
      const recipeIds = [1, 3]

      weekPageContainer.find('[data-create-cart-button]').at(0).simulate('click')

      expect(context.dispatch).toHaveBeenLastCalledWith(NewCartActions.createCart(recipeIds, expect.any(Function)))
    })

    it('redirects to the new cart on create cart button click', () => {
      jest
        .spyOn(NewCartActions, 'createCart')
        .mockImplementation((recipeIds, onSuccess) => onSuccess({ id: 34 }))

      weekPageContainer.find('[data-create-cart-button]').at(0).simulate('click')

      expect(history.push).toHaveBeenCalledWith('/carts/34')
    })
  })

  describe('when previous button clicked', () => {
    it('calls history.push', () => {
      mockAppContext()

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

      const weekPage = mount(<WeekPage weekNumber={weekNumber} year={year} history={history} />)

      weekPage.find('.week-page__previous-week').at(0).simulate('click')

      expect(history.push).toHaveBeenCalledWith(`/weeks/${currentWeek.year}/${currentWeek.weekNumber - 1}`)
    })

    it('calculates the previous year if weekNumber = 1', () => {
      mockAppContext()
      const week = '1'
      const testWeek = {
        ...currentWeek,
        weekNumber: 1,
      }
      const pushSpy = history.push

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(testWeek))

      const weekPage = mount(<WeekPage weekNumber={week} year={year} history={history} />)

      weekPage.find('.week-page__previous-week').at(0).simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${testWeek.year - 1}/${WEEKS_IN_A_YEAR}`)
    })
  })

  describe('when next button clicked', () => {
    it('calls history.push', () => {
      mockAppContext()

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

      const weekPage = mount(<WeekPage weekNumber={weekNumber} year={year} history={history} />)

      weekPage.find('.week-page__next-week').at(0).simulate('click')

      expect(history.push).toHaveBeenCalledWith(`/weeks/${currentWeek.year}/${currentWeek.weekNumber + 1}`)
    })

    it('calculates the next year if weekNumber = MAX_WEEK', () => {
      mockAppContext()
      const week = `${WEEKS_IN_A_YEAR}`
      const testWeek = {
        ...currentWeek,
        weekNumber: WEEKS_IN_A_YEAR,
      }

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(testWeek))

      const weekPage = mount(<WeekPage weekNumber={week} year={year} history={history} />)

      weekPage.find('.week-page__next-week').at(0).simulate('click')

      expect(history.push).toHaveBeenCalledWith(`/weeks/${testWeek.year + 1}/1`)
    })
  })

  it('show a modal on meal click', () => {
    mockAppContext()

    jest
      .spyOn(WeekActions, 'getWeek')
      .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

    const weekPage = mount(<WeekPage weekNumber={weekNumber} year={year} history={history} />)

    expect(weekPage.find('[data-recipe-modal]').at(0).props().open).toBeFalsy()

    weekPage.find('[data-meal="lunch-MONDAY"] button').at(0).simulate('click')

    expect(weekPage.find('[data-recipe-modal]').at(0).props().open).toBeTruthy()
  })

  it('calls saveWeek when recipe is set', () => {
    const context = mockAppContext()

    jest
      .spyOn(WeekActions, 'getWeek')
      .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

    jest
      .spyOn(RecipesActions, 'getAllRecipes')
      .mockImplementation((onSuccess) => onSuccess({ recipes: [{ id: 2, imgUrl: 'dummy' }] }))

    const weekPage = mount(<WeekPage weekNumber={weekNumber} year={year} history={history} />)

    weekPage.find('[data-meal="lunch-MONDAY"] button').at(0).simulate('click')
    weekPage.find('[data-modal-recipe-container]').at(0).simulate('click')

    expect(context.dispatch).toHaveBeenLastCalledWith(saveWeek(currentWeek, expect.any(Function)))
  })
})
