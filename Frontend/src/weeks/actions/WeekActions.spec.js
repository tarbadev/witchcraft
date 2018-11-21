import { getWeekSuccess, getWeek, getCurrentWeek } from './WeekActions'
import { weekNumber } from 'weeknumber'

describe('WeekActions', () => {
  describe('getWeek', () => {
    it('getWeek sends a request to retrieve a week', () => {
      const dispatchSpy = jest.fn()

      getWeek(2018, 33)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'FETCH',
        url: '/api/weeks/2018/33',
        method: 'GET',
        body: undefined,
        onSuccess: getWeekSuccess,
        onError: undefined,
      })
    })
  })

  describe('getWeekSuccess', () => {
    it('getWeekSuccess sends a request to retrieve a week', () => {
      const dispatchSpy = jest.fn()
      const week = { id: 1, recipes: [] }

      getWeekSuccess(week)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'week',
        payload: week,
      })
    })
  })

  describe('getCurrentWeek', () => {
    it('returns the current week and year', () => {
      expect(getCurrentWeek()).toEqual({ week: weekNumber(), year: new Date().getYear() + 1900 })
    })
  })
})
