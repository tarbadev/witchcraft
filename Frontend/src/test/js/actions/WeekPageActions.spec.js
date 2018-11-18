import { toggleModal, saveWeek, saveWeekSuccess } from 'app-actions/WeekPageActions'
import { setState } from 'app-root/RootReducer'
import { fetchAction } from 'app-root/WitchcraftMiddleware'

describe('WeekPageActions', () => {
  it('toggleModal changes the current state', () => {
    const dispatchSpy = jasmine.createSpy()
    const day = 'Monday'
    const meal = 'Lunch'
    const recipeId = 23
    const newModal = {
      isModalOpen: true,
      day: day,
      meal: meal,
      currentRecipeId: recipeId,
    }

    toggleModal(true, day, meal, recipeId)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith(setState('weekPage.modal', newModal))
  })

  describe('saveWeek', () => {
    it('calls the save week API', () => {
      const dispatchSpy = jasmine.createSpy()
      const week = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [],
      }

      saveWeek(week)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: '/api/weeks/2018/33',
        method: 'POST',
        body: week,
        onSuccess: saveWeekSuccess,
      }))
    })
  })

  describe('saveWeekSuccess', () => {
    it('success callback saves week in state', () => {
      const dispatchSpy = jasmine.createSpy()
      const week = {
        id: 12,
        year: 2018,
        weekNumber: 33,
        days: [],
      }
      saveWeekSuccess(week)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'week',
        payload: week,
      })
    })
  })
})
