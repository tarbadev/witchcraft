import { fetchAction } from 'app-root/WitchcraftMiddleware'
import { setState } from 'app-root/RootReducer'

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
