import { setRecipe } from 'app-actions/RecipeListModalActions'
import { setState } from 'app-root/RootReducer'

describe('RecipeListModalActions', () => {
  describe('setRecipe', () => {
    it('sets the meal on a given day based on the recipe and closes modal', () => {
      const dispatchSpy = jest.fn()
      const day = 'Monday'
      const meal = 'Lunch'
      const recipe = { name: 'recipeName' }
      const newModal = {
        isModalOpen: false,
        day: '',
        meal: '',
        currentRecipe: 0,
      }

      setRecipe(recipe, day, meal)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('week.days.0.lunch', recipe))
      expect(dispatchSpy).toHaveBeenCalledWith(setState('weekPage.modal', newModal))
    })
  })
})
