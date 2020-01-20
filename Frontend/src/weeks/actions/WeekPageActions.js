import { fetchAction } from 'src/app/WitchcraftMiddleware'

export const toggleModal = (isModalOpen, day = '', meal = '', currentRecipeIds = []) =>
  ({
    isModalOpen: isModalOpen,
    day: day,
    meal: meal,
    currentRecipeIds: currentRecipeIds,
    displayExpressRecipeForm: false,
  })

export const saveWeek = (week, onSuccess) => {
  const weekRequest = {
    id: week.id,
    year: week.year,
    weekNumber: week.weekNumber,
    days: week.days.map(day => ({
      id: day.id,
      name: day.name,
      lunch: day.lunch.map(meal => ({ recipeId: meal.id, mealId: meal.mealId })),
      diner: day.diner.map(meal => ({ recipeId: meal.id, mealId: meal.mealId })),
    })),
  }

  return fetchAction({
    url: `/api/weeks/${week.year}/${week.weekNumber}`,
    method: 'POST',
    body: weekRequest,
    onSuccess,
  })
}


const days = [
  'monday',
  'tuesday',
  'wednesday',
  'thursday',
  'friday',
  'saturday',
  'sunday',
]

export const setRecipeToWeek = (week, recipe, day, meal) => {
  const newWeek = {
    ...week,
    days: [
      ...week.days,
    ],
  }
  const dayIndex = days.indexOf(day.toLowerCase())

  if (meal === 'lunch') {
    newWeek.days[dayIndex].lunch = [...newWeek.days[dayIndex].lunch, recipe]
  } else if (meal === 'diner') {
    newWeek.days[dayIndex].diner = [...newWeek.days[dayIndex].diner, recipe]
  }

  return newWeek
}
