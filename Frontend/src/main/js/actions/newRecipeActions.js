import { setState } from "app-root/RootReducer"
import { request } from 'app-root/Utils'
import { history } from 'app-root/Store'

export const urlInputChange = (url) => {
  return (dispatch) => {
    return dispatch(setState("newRecipe.form.url", url))
	}
}

export const submitUrlForm = (form) => {
  return (dispatch, getState) => {
    request(
      {
        url: '/api/recipes/importFromUrl',
        method: 'post',
        body: JSON.stringify(form)
      }
    ).then((recipe) => {
      return dispatch(setState("newRecipe.form.recipeAdded", true))
    }
  )
  }
}
