import { saveWeek, setRecipeToWeek, toggleModal } from './WeekPageActions'
import { fetchAction } from 'src/app/WitchcraftMiddleware'

describe('WeekPageActions', () => {
  it('toggleModal returns the new modal object', () => {
    const day = 'Monday'
    const meal = 'Lunch'
    const recipeIds = [23]
    const newModal = {
      isModalOpen: true,
      day: day,
      meal: meal,
      currentRecipeIds: recipeIds,
      displayExpressRecipeForm: false,
    }

    expect(toggleModal(true, day, meal, recipeIds)).toEqual(newModal)
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

    it('sends only the id of the recipes and mealId if exists', () => {
      const onSuccessSpy = jest.fn()
      const week = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [
          {
            lunch: [],
            diner: [{ id: 34, mealId: 45 }],
          },
          {
            lunch: [{ id: 4 }],
            diner: [],
          },
        ],
      }
      const weekRequest = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [
          {
            lunch: [],
            diner: [{ recipeId: 34, mealId: 45 }],
          },
          {
            lunch: [{ recipeId: 4 }],
            diner: [],
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
            lunch: [],
            diner: [{ id: 34 }],
          },
          {
            name: 'tuesday',
            lunch: [{ id: 4 }],
            diner: [],
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
            lunch: [recipe],
            diner: [{ id: 34 }],
          },
          {
            name: 'tuesday',
            lunch: [{ id: 4 }],
            diner: [],
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
            lunch: [recipe],
            diner: [{ id: 34 }],
          },
          {
            name: 'tuesday',
            lunch: [{ id: 4 }],
            diner: [recipe],
          },
        ],
      }

      expect(setRecipeToWeek(week, recipe, 'monday', 'lunch')).toEqual(newWeek)
      expect(setRecipeToWeek(newWeek, recipe, 'tuesday', 'diner')).toEqual(newWeek2)
    })

    it('Returns a new week without the removed recipe', () => {
      const recipe = { id: 4, name: 'lasagna' }
      const week = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [
          {
            name: 'monday',
            lunch: [],
            diner: [{ id: 34 }],
          },
          {
            name: 'tuesday',
            lunch: [{ id: 4 }],
            diner: [],
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
            lunch: [recipe],
            diner: [{ id: 34 }],
          },
          {
            name: 'tuesday',
            lunch: [{ id: 4 }],
            diner: [],
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
            lunch: [recipe],
            diner: [{ id: 34 }],
          },
          {
            name: 'tuesday',
            lunch: [{ id: 4 }],
            diner: [recipe],
          },
        ],
      }

      expect(setRecipeToWeek(week, recipe, 'monday', 'lunch')).toEqual(newWeek)
      expect(setRecipeToWeek(newWeek, recipe, 'tuesday', 'diner')).toEqual(newWeek2)
    })
  })
})
