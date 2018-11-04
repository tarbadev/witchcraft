import {setState} from "app-root/RootReducer"
import {fetchAction} from 'app-root/WitchcraftMiddleware'
import { push } from 'connected-react-router'

export const getRecipeSuccess = data => dispatch => {
  dispatch(setState('recipe', data))

  const form = {
    id: data.id,
    name: data.name,
    url: data.originUrl,
    imgUrl: data.imgUrl,
    ingredients: data.ingredients,
    steps: data.steps,
  }
  dispatch(setState('editRecipe.form', form))
}

export const getRecipeError = error => dispatch => {
  dispatch(push('#/recipes'))
}

export const getRecipe = id => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${id}`,
    method: 'GET',
    onSuccess: getRecipeSuccess,
    onError: getRecipeError
  }))
}

export const setFavoriteSuccess = data => dispatch => {
  dispatch(setState('recipe', data))
}

export const setFavorite = (id, isFavorite) => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${id}`,
    method: 'PATCH',
    body: {
      favorite: isFavorite
    },
    onSuccess: setFavoriteSuccess
  }))
}

export const deleteRecipeCallback = data => dispatch => {
  dispatch(setState('recipePage.isDeleting', false))
  dispatch(push('#/recipes'))
}

export const deleteRecipe = id => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${id}`,
    method: 'DELETE',
    onSuccess: deleteRecipeCallback,
    onError: deleteRecipeCallback
  }))
}

export const updateRecipeSuccess = data => dispatch => {
  dispatch(push(`#/recipes/${data.id}`))
}

export const updateRecipe = (form) => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${form.id}/update`,
    method: 'PUT',
    body: form,
    onSuccess: updateRecipeSuccess,
  }))
}