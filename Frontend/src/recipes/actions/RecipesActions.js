import { setState } from 'src/RootReducer'
import { fetchAction } from 'src/WitchcraftMiddleware'

export const getFavoriteRecipes = onSuccess =>
  fetchAction({
    url: '/api/recipes/favorites',
    method: 'GET',
    onSuccess,
  })

export const getLatestRecipes = onSuccess =>
  fetchAction({
    url: '/api/recipes/latest',
    method: 'GET',
    onSuccess: onSuccess,
  })

export const getAllRecipesSuccess = data => dispatch => {
  dispatch(setState('allRecipes', data.recipes))
  dispatch(setState('recipes', data.recipes))
}

export const getAllRecipes = () => dispatch => {
  dispatch(fetchAction({
    url: '/api/recipes',
    method: 'GET',
    onSuccess: getAllRecipesSuccess,
  }))
}

export const filterRecipes = (search) => {
  return (dispatch, getState) => {
    const filteredRecipes = getState().app.allRecipes.filter((recipe) => {
      return recipe.name.toLowerCase().includes(search.toLowerCase())
    })
    dispatch(setState('recipes', filteredRecipes))
  }
}
