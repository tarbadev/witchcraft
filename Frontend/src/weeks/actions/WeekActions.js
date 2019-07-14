import { fetchAction } from 'src/WitchcraftMiddleware'
import { weekNumber } from 'weeknumber'
import { setState } from '../../RootReducer'

export const getWeekSuccess = week => {
  setState('week', week)
}

export const getWeek = (year, week, onSuccess = getWeekSuccess) =>
  fetchAction({
    type: 'FETCH',
    url: `/api/weeks/${year}/${week}`,
    method: 'GET',
    onSuccess: onSuccess,
  })

export const getCurrentWeek = () => {
  const week = weekNumber()
  const year = new Date().getYear() + 1900
  return { year, week }
}
