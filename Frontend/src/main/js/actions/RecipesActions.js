import {setState} from "app-root/RootReducer"
import { fetchAction } from 'app-root/WitchcraftMiddleware'

export const getAllRecipesSuccess = data => dispatch => {
  dispatch(setState('allRecipes', data.recipes))
  dispatch(setState('recipes', data.recipes))
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
      return recipe.name.toLowerCase().includes(search.toLowerCase());
    })
    dispatch(setState('recipes', filteredRecipes))
  }
}
