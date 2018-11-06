import React from 'react'
import {shallow} from 'enzyme'

import { WeekPage, WEEKS_IN_A_YEAR } from 'app-components/WeekPage'

describe('WeekPage', () => {
  describe('when previous button clicked', () => {
    it('calls history.push when Modify button clicked', () => {
      const pushSpy = jasmine.createSpy()
      const week = { year: 2018, weekNumber: 45, days: [] }
      const weekPage = shallow(<WeekPage history={{push: pushSpy}} week={week} />)

      weekPage.find('.week-page__previous-week').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${week.year}/${week.weekNumber - 1}`)
    })

    it('calculates the previous year if weekNumber = 1 when Modify button clicked', () => {
      const pushSpy = jasmine.createSpy()
      const week = { year: 2018, weekNumber: 1, days: [] }
      const weekPage = shallow(<WeekPage history={{push: pushSpy}} week={week} />)

      weekPage.find('.week-page__previous-week').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${week.year - 1}/${WEEKS_IN_A_YEAR}`)
    })
  })

  describe('when next button clicked', () => {
    it('calls history.push when Modify button clicked', () => {
      const pushSpy = jasmine.createSpy()
      const week = { year: 2018, weekNumber: 45, days: [] }
      const weekPage = shallow(<WeekPage history={{push: pushSpy}} week={week} />)

      weekPage.find('.week-page__next-week').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${week.year}/${week.weekNumber + 1}`)
    })

    it('calculates the next year if weekNumber = MAX_WEEK when Modify button clicked', () => {
      const pushSpy = jasmine.createSpy()
      const week = { year: 2018, weekNumber: WEEKS_IN_A_YEAR, days: [] }
      const weekPage = shallow(<WeekPage history={{push: pushSpy}} week={week} />)

      weekPage.find('.week-page__next-week').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/weeks/${week.year + 1}/1`)
    })
  })
})
