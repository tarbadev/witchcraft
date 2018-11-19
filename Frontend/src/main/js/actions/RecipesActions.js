import { setState } from 'app-root/RootReducer'
import { fetchAction } from 'app-root/WitchcraftMiddleware'

export const getFavoriteRecipesSuccess = favorites => dispatch => {
  dispatch(setState('homePage.favoriteRecipes', favorites))
}

export const getFavoriteRecipes = () => (dispatch) => {
  dispatch(fetchAction({
    url: '/api/recipes/favorites',
    method: 'GET',
    onSuccess: getFavoriteRecipesSuccess
  }))
}

export const getAllRecipesSuccess = data => dispatch => {
  dispatch(setState('allRecipes', data.recipes))
  dispatch(setState('recipes', data.recipes))
  dispatch(setState('newCartPage.form', data.recipes.map(recipe => ({ id: recipe.id, name: recipe.name, selected: false }))))
}

export const getAllRecipes = () => (dispatch) => {
  dispatch(fetchAction({
    url: '/api/recipes',
    method: 'GET',
    onSuccess: getAllRecipesSuccess
  }))
}

export const filterRecipes = (search) => {
  return(dispatch, getState) => {
    const filteredRecipes = getState().allRecipes.filter((recipe) => {
      return recipe.name.toLowerCase().includes(search.toLowerCase())
    })
    dispatch(setState('recipes', filteredRecipes))
  }
}
