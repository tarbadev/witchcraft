import React from 'react'
import { shallow } from 'enzyme'

import { WeekPage, WEEKS_IN_A_YEAR } from './WeekPage'

describe('WeekPage', () => {
  describe('when previous button clicked', () => {
    it('calls history.push', () => {
      const pushSpy = jest.fn()
      const week = { year: 2018, weekNumber: 45, days: [] }
      const weekPage = shallow(<WeekPage history={{ push: pushSpy }} week={week} />)

      weekPage.find('.week-page__previous-week').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${week.year}/${week.weekNumber - 1}`)
    })

    it('calculates the previous year if weekNumber = 1', () => {
      const pushSpy = jest.fn()
      const week = { year: 2018, weekNumber: 1, days: [] }
      const weekPage = shallow(<WeekPage history={{ push: pushSpy }} week={week} />)

      weekPage.find('.week-page__previous-week').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${week.year - 1}/${WEEKS_IN_A_YEAR}`)
    })
  })

  describe('when next button clicked', () => {
    it('calls history.push', () => {
      const pushSpy = jest.fn()
      const week = { year: 2018, weekNumber: 45, days: [] }
      const weekPage = shallow(<WeekPage history={{ push: pushSpy }} week={week} />)

      weekPage.find('.week-page__next-week').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${week.year}/${week.weekNumber + 1}`)
    })

    it('calculates the next year if weekNumber = MAX_WEEK', () => {
      const pushSpy = jest.fn()
      const week = { year: 2018, weekNumber: WEEKS_IN_A_YEAR, days: [] }
      const weekPage = shallow(<WeekPage history={{ push: pushSpy }} week={week} />)

      weekPage.find('.week-page__next-week').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${week.year + 1}/1`)
    })
  })

  describe('when save button clicked', () => {
    it('calls saveWeek', () => {
      const onSaveClick = jest.fn()
      const week = { year: 2018, weekNumber: 45, days: [] }
      const weekPage = shallow(<WeekPage onSaveClick={onSaveClick} week={week} />)

      weekPage.find('.week-page__save-button').simulate('click')

      expect(onSaveClick).toHaveBeenCalled()
    })
  })

  describe('when create Cart button clicked', () => {
    it('calls createCartFromWeek', () => {
      const createCartFromWeek = jest.fn()
      const week = {
        year: 2018,
        weekNumber: 45,
        days: [
          {
            lunch: { id: 1 },
            diner: { id: 3 },
          },
          {
            lunch: { id: 9 },
            diner: { id: 0 },
          },
          {
            lunch: null,
            diner: null,
          },
        ],
      }
      const recipeIds = [
        { id: 1 },
        { id: 3 },
        { id: 9 },
      ]
      const weekPage = shallow(<WeekPage createCart={createCartFromWeek} week={week} />)

      weekPage.find('.week-page__create-cart-button').simulate('click')

      expect(createCartFromWeek).toHaveBeenCalledWith(recipeIds)
    })
  })
})
