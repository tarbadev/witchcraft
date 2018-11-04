import { setState } from "app-root/RootReducer"
import { request } from 'app-root/Utils'
import { history } from 'app-root/Store'

export const formInputChange = (key, value) => {
  return (dispatch) => {
    return dispatch(setState(`newRecipe.forms.${key}`, value))
	}
}

export const submitForm = (url, form) => {
  return (dispatch) => {
    request(
      {
        url: url,
        method: 'post',
        body: JSON.stringify(form)
      }
    ).then((recipe) => {
      return dispatch(setState("newRecipe.forms.recipeAdded", true))
    }
  )
  }
}
