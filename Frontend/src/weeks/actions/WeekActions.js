import { fetchAction } from 'src/WitchcraftMiddleware'
import { setState } from 'src/RootReducer'
import { weekNumber } from 'weeknumber'

export const getWeekSuccess = week => dispatch => {
  dispatch(setState('week', week))
}

export const getWeek = (year, week) => dispatch => {
  dispatch(fetchAction({
    type: 'FETCH',
    url: `/api/weeks/${year}/${week}`,
    method: 'GET',
    onSuccess: getWeekSuccess,
  }))
}

export const getCurrentWeek = () => {
  const week = weekNumber()
  const year = new Date().getYear() + 1900
  return { year, week }
}
