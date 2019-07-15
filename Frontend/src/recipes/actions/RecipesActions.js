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

export const getAllRecipes = onSuccess =>
  fetchAction({
    url: '/api/recipes',
    method: 'GET',
    onSuccess,
  })

export const filterRecipes = (search) => {
  return (dispatch, getState) => {
    const filteredRecipes = getState().app.allRecipes.filter((recipe) => {
      return recipe.name.toLowerCase().includes(search.toLowerCase())
    })
    dispatch(setState('recipes', filteredRecipes))
  }
}
