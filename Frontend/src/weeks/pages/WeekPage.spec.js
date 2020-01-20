import React from 'react'
import { mount } from 'enzyme'

import { WeekPageContainer, WEEKS_IN_A_YEAR } from './WeekPage'
import { mockAppContext } from 'src/testUtils'
import * as WeekActions from 'src/weeks/actions/WeekActions'
import * as NewCartActions from 'src/carts/actions/NewCartActions'
import { saveWeek } from '../actions/WeekPageActions'

describe('WeekPageContainer', () => {
  it('loads the week when mounting the component', () => {
    const context = mockAppContext()
    const year = '2019'
    const week = '34'

    mount(<WeekPageContainer weekNumber={week} year={year} />)

    expect(context.dispatch).toHaveBeenCalledWith(WeekActions.getWeek(year, week, expect.any(Function)))
  })

  it('calls saveWeek on save button click', () => {
    const context = mockAppContext()
    const year = 2019
    const week = 34
    const currentWeek = {
      year,
      weekNumber: week,
      days: [
        {
          lunch: [{ id: 1 }],
          diner: [{ id: 3 }],
        },
      ],
    }

    jest
      .spyOn(WeekActions, 'getWeek')
      .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

    const weekPage = mount(<WeekPageContainer match={{ params: { year, week } }} />)

    weekPage.find('.week-page__save-button button').simulate('click')

    expect(context.dispatch).toHaveBeenCalledWith(saveWeek(currentWeek, expect.any(Function)))
  })

  it('calls saveWeek on create cart button click', () => {
    const context = mockAppContext()
    const year = 2019
    const week = 34
    const currentWeek = {
      year,
      weekNumber: week,
      days: [
        {
          lunch: [{ id: 1 }],
          diner: [{ id: 3 }],
        },
        {
          lunch: [],
          diner: [],
        },
      ],
    }

    jest
      .spyOn(WeekActions, 'getWeek')
      .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

    const weekPage = mount(<WeekPageContainer match={{ params: { year, week } }} />)

    weekPage.find('.week-page__create-cart-button button').simulate('click')

    expect(context.dispatch).toHaveBeenNthCalledWith(3, saveWeek(currentWeek, expect.any(Function)))
  })

  it('calls createCartFromWeek on create cart button click', () => {
    const context = mockAppContext()
    const year = 2019
    const week = 34
    const currentWeek = {
      year,
      weekNumber: week,
      days: [
        {
          lunch: [{ mealId: 78, id: 1 }],
          diner: [{ mealId: 12, id: 3 }],
        },
        {
          lunch: [],
          diner: [],
        },
      ],
    }
    const recipeIds = [1, 3]

    jest
      .spyOn(WeekActions, 'getWeek')
      .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

    const weekPage = mount(<WeekPageContainer match={{ params: { year, week } }} />)

    weekPage.find('.week-page__create-cart-button button').simulate('click')

    expect(context.dispatch).toHaveBeenNthCalledWith(4, NewCartActions.createCart(recipeIds, expect.any(Function)))
  })

  it('redirects to the new cart on create cart button click', () => {
    mockAppContext()
    const year = 2019
    const week = 34
    const currentWeek = {
      year,
      weekNumber: week,
      days: [
        {
          lunch: [{ id: 1 }],
          diner: [{ id: 3 }],
        },
        {
          lunch: [],
          diner: [],
        },
      ],
    }
    const pushSpy = jest.fn()

    jest
      .spyOn(WeekActions, 'getWeek')
      .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

    jest
      .spyOn(NewCartActions, 'createCart')
      .mockImplementation((recipeIds, onSuccess) => onSuccess({ id: 34 }))

    const weekPage = mount(<WeekPageContainer match={{ params: { year, week } }} history={{ push: pushSpy }} />)

    weekPage.find('.week-page__create-cart-button button').simulate('click')

    expect(pushSpy).toHaveBeenCalledWith('/carts/34')
  })

  describe('when previous button clicked', () => {
    it('calls history.push', () => {
      mockAppContext()
      const year = 2019
      const week = 34
      const currentWeek = {
        year,
        weekNumber: week,
        days: [
          {
            lunch: [{ id: 1 }],
            diner: [{ id: 3 }],
          },
          {
            lunch: [],
            diner: [],
          },
        ],
      }
      const pushSpy = jest.fn()

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

      const weekPage = mount(<WeekPageContainer history={{ push: pushSpy }} match={{ params: { year, week } }} />)

      weekPage.find('.week-page__previous-week').at(0).simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${currentWeek.year}/${currentWeek.weekNumber - 1}`)
    })

    it('calculates the previous year if weekNumber = 1', () => {
      mockAppContext()
      const year = 2019
      const week = 1
      const currentWeek = {
        year,
        weekNumber: week,
        days: [
          {
            lunch: [{ id: 1 }],
            diner: [{ id: 3 }],
          },
          {
            lunch: [],
            diner: [],
          },
        ],
      }
      const pushSpy = jest.fn()

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

      const weekPage = mount(<WeekPageContainer history={{ push: pushSpy }} match={{ params: { year, week } }} />)

      weekPage.find('.week-page__previous-week').at(0).simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${currentWeek.year - 1}/${WEEKS_IN_A_YEAR}`)
    })
  })

  describe('when next button clicked', () => {
    it('calls history.push', () => {
      mockAppContext()
      const year = 2019
      const week = 45
      const currentWeek = {
        year,
        weekNumber: week,
        days: [
          {
            lunch: [{ id: 1 }],
            diner: [{ id: 3 }],
          },
          {
            lunch: [],
            diner: [],
          },
        ],
      }
      const pushSpy = jest.fn()

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

      const weekPage = mount(<WeekPageContainer history={{ push: pushSpy }} match={{ params: { year, week } }} />)

      weekPage.find('.week-page__next-week').at(0).simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${currentWeek.year}/${currentWeek.weekNumber + 1}`)
    })

    it('calculates the next year if weekNumber = MAX_WEEK', () => {
      mockAppContext()
      const year = 2019
      const week = WEEKS_IN_A_YEAR
      const currentWeek = {
        year,
        weekNumber: week,
        days: [
          {
            lunch: [{ id: 1 }],
            diner: [{ id: 3 }],
          },
          {
            lunch: [],
            diner: [],
          },
        ],
      }
      const pushSpy = jest.fn()

      jest
        .spyOn(WeekActions, 'getWeek')
        .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

      const weekPage = mount(<WeekPageContainer history={{ push: pushSpy }} match={{ params: { year, week } }} />)

      weekPage.find('.week-page__next-week').at(0).simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${currentWeek.year + 1}/1`)
    })
  })

  it('show a modal on meal click', () => {
    mockAppContext()
    const year = 2019
    const week = 34
    const currentWeek = {
      year,
      weekNumber: week,
      days: [
        {
          name: 'MONDAY',
          lunch: [{ id: 0,  }],
          diner: [{ id: 0,  }],
        },
        {
          name: 'TUESDAY',
          lunch: [],
          diner: [],
        },
      ],
    }

    jest
      .spyOn(WeekActions, 'getWeek')
      .mockImplementation((year, week, onSuccess) => onSuccess(currentWeek))

    const weekPage = mount(<WeekPageContainer match={{ params: { year, week } }} />)

    expect(weekPage.find('Modal').props().open).toBeFalsy()

    weekPage.find('[data-meal="lunch-MONDAY"]').at(0).simulate('click')

    expect(weekPage.find('Modal').at(0).props().open).toBeTruthy()
  })
})
