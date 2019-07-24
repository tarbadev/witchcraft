import { getCurrentWeek, getWeek } from './WeekActions'
import { weekNumber } from 'weeknumber'
import { fetchAction } from 'src/app/WitchcraftMiddleware'

describe('WeekActions', () => {
  describe('getWeek', () => {
    it('getWeek sends a request to retrieve a week', () => {
      const onSuccessSpy = jest.fn()

      expect(getWeek(2018, 33, onSuccessSpy)).toEqual(fetchAction({
        url: '/api/weeks/2018/33',
        method: 'GET',
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('getCurrentWeek', () => {
    it('returns the current week and year', () => {
      expect(getCurrentWeek()).toEqual({ week: weekNumber(), year: new Date().getYear() + 1900 })
    })
  })
})
