import { fetchAction } from 'src/WitchcraftMiddleware'

export const toggleModal = (isModalOpen, day = '', meal = '', currentRecipeId) =>
  ({
    isModalOpen: isModalOpen,
    day: day,
    meal: meal,
    currentRecipeId: currentRecipeId,
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
      lunch: day.lunch?.id,
      diner: day.diner?.id,
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
    newWeek.days[dayIndex].lunch = recipe
  } else if (meal === 'diner') {
    newWeek.days[dayIndex].diner = recipe
  }

  return newWeek
}
