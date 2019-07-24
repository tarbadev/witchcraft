import { saveWeek, setRecipeToWeek, toggleModal } from './WeekPageActions'
import { fetchAction } from 'src/app/WitchcraftMiddleware'

describe('WeekPageActions', () => {
  it('toggleModal returns the new modal object', () => {
    const day = 'Monday'
    const meal = 'Lunch'
    const recipeId = 23
    const newModal = {
      isModalOpen: true,
      day: day,
      meal: meal,
      currentRecipeId: recipeId,
      displayExpressRecipeForm: false,
    }

    expect(toggleModal(true, day, meal, recipeId)).toEqual(newModal)
  })

  describe('saveWeek', () => {
    it('calls the save week API', () => {
      const onSuccessSpy = jest.fn()
      const week = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [],
      }

      expect(saveWeek(week, onSuccessSpy)).toEqual(fetchAction({
        url: '/api/weeks/2018/33',
        method: 'POST',
        body: week,
        onSuccess: onSuccessSpy,
      }))
    })

    it('sends only the id of the recipes, not the object', () => {
      const onSuccessSpy = jest.fn()
      const week = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [
          {
            diner: { id: 34 },
          },
          {
            lunch: { id: 4 },
          },
        ],
      }
      const weekRequest = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [
          {
            diner: 34,
          },
          {
            lunch: 4,
          },
        ],
      }

      expect(saveWeek(week, onSuccessSpy)).toEqual(fetchAction({
        url: '/api/weeks/2018/33',
        method: 'POST',
        body: weekRequest,
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('setRecipeToWeek', () => {
    it('Returns a new week with the given recipe', () => {
      const recipe = { id: 54, name: 'lasagna' }
      const week = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [
          {
            name: 'monday',
            diner: { id: 34 },
          },
          {
            name: 'tuesday',
            lunch: { id: 4 },
          },
        ],
      }
      const newWeek = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [
          {
            name: 'monday',
            lunch: recipe,
            diner: { id: 34 },
          },
          {
            name: 'tuesday',
            lunch: { id: 4 },
          },
        ],
      }
      const newWeek2 = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [
          {
            name: 'monday',
            lunch: recipe,
            diner: { id: 34 },
          },
          {
            name: 'tuesday',
            lunch: { id: 4 },
            diner: recipe,
          },
        ],
      }

      expect(setRecipeToWeek(week, recipe, 'monday', 'lunch')).toEqual(newWeek)
      expect(setRecipeToWeek(newWeek, recipe, 'tuesday', 'diner')).toEqual(newWeek2)
    })
  })
})
