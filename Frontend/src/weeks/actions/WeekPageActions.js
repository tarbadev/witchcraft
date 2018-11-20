import { setState } from 'src/RootReducer'
import { fetchAction } from 'src/WitchcraftMiddleware'

export const toggleModal = (isModalOpen, day = '', meal = '', currentRecipeId) => dispatch => {
  const newModal = {
    isModalOpen: isModalOpen,
    day: day,
    meal: meal,
    currentRecipeId: currentRecipeId,
  }
  dispatch(setState('weekPage.modal', newModal))
}

export const saveWeekSuccess = week => dispatch => {
  dispatch(setState('week', week))
}

export const saveWeek = week => dispatch => {
  dispatch(fetchAction(
    {
      url: `/api/weeks/${week.year}/${week.weekNumber}`,
      method: 'POST',
      body: week,
      onSuccess: saveWeekSuccess,
    }
  ))
}
