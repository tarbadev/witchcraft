import { fetchAction } from 'src/WitchcraftMiddleware'
import { setState } from 'src/RootReducer'

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
