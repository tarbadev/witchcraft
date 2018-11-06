import { getWeekSuccess, getWeek } from 'app-actions/WeekActions'

describe('WeekActions', () => {
  it('getWeek sends a request to retrieve a week', () => {
    const dispatchSpy = jasmine.createSpy()

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

  it('getWeekSuccess sends a request to retrieve a week', () => {
    const dispatchSpy = jasmine.createSpy()
    const week = { id: 1, recipes: [] }

    getWeekSuccess(week)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: 'SET_STATE',
      key: 'week',
      payload: week,
    })
  })
})
