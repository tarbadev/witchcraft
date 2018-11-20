import { setState } from 'src/RootReducer'
import { fetchAction } from 'src/WitchcraftMiddleware'

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

export const getLatestRecipesSuccess = latest => dispatch => {
  dispatch(setState('homePage.latestRecipes', latest))
}

export const getLatestRecipes = () => (dispatch) => {
  dispatch(fetchAction({
    url: '/api/recipes/latest',
    method: 'GET',
    onSuccess: getLatestRecipesSuccess
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
