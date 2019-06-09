import { setState } from 'src/RootReducer'
import { fetchAction } from 'src/WitchcraftMiddleware'

export const toggleModal = (isModalOpen, day = '', meal = '', currentRecipeId) => dispatch => {
  const newModal = {
    isModalOpen: isModalOpen,
    day: day,
    meal: meal,
    currentRecipeId: currentRecipeId,
    displayExpressRecipeForm: false,
  }
  dispatch(setState('weekPage.modal', newModal))
}

export const saveWeekSuccess = week => dispatch => {
  dispatch(setState('week', week))
  dispatch(setState('weekPage.showSuccessMessage', true))
}

export const saveWeek = week => dispatch => {
  const weekRequest = {
    id: week.id,
    year: week.year,
    weekNumber: week.weekNumber,
    days: week.days.map(day => ({
      id: day.id,
      name: day.name,
      lunch: day.lunch?.id,
      diner: day.diner?.id,
    }))
  }

  dispatch(fetchAction(
    {
      url: `/api/weeks/${week.year}/${week.weekNumber}`,
      method: 'POST',
      body: weekRequest,
      onSuccess: saveWeekSuccess,
    },
  ))
}
