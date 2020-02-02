import { fetchAction } from 'src/app/WitchcraftMiddleware'
import { weekNumber } from 'weeknumber'
import { setState } from 'src/app/RootReducer'

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

export const getCurrentDayIndex = () => {
  let dayIndex = new Date().getDay()
  if (dayIndex === 0) {
    dayIndex = 6
  } else {
    dayIndex--
  }

  return dayIndex
}
