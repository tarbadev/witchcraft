import { setState } from 'src/RootReducer'
import { fetchAction } from '../../WitchcraftMiddleware'

const days = [
  'monday',
  'tuesday',
  'wednesday',
  'thursday',
  'friday',
  'saturday',
  'sunday',
]

export const setRecipe = (recipe, day, meal) => dispatch => {
  const modal = {
    isModalOpen: false,
    day: '',
    meal: '',
    currentRecipe: 0,
  }
  const dayIndex = days.indexOf(day.toLowerCase())
  dispatch(setState(`week.days.${dayIndex}.${meal.toLowerCase()}`, recipe))
  dispatch(setState('weekPage.modal', modal))
}

export const addExpressRecipe = (recipeName, day, meal) => dispatch => {
  dispatch(fetchAction({
    url: '/recipes/express',
    method: 'POST',
    body: { name: recipeName },
    onSuccess: recipe => setRecipe(recipe, day, meal),
  }))
}