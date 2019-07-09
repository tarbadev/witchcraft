import { setState } from 'src/RootReducer'
import { fetchAction } from 'src/WitchcraftMiddleware'
import { push } from 'connected-react-router'

export const getRecipeSuccess = data => dispatch => {
  dispatch(setState('recipe', data))
  dispatch(setState('editRecipe.form', data))
}

export const getRecipeError = () => dispatch => {
  dispatch(push('/recipes'))
}

export const getRecipe = id => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${id}`,
    method: 'GET',
    onSuccess: getRecipeSuccess,
    onError: getRecipeError,
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
      favorite: isFavorite,
    },
    onSuccess: setFavoriteSuccess,
  }))
}

export const deleteRecipeCallback = () => dispatch => {
  dispatch(setState('pages.recipePage.isDeleting', false))
  dispatch(push('/recipes'))
}

export const deleteRecipe = id => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${id}`,
    method: 'DELETE',
    onSuccess: deleteRecipeCallback,
    onError: deleteRecipeCallback,
  }))
}

export const updateRecipeSuccess = data => dispatch => {
  dispatch(push(`/recipes/${data.id}`))
}

export const updateRecipe = (form) => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${form.id}/update`,
    method: 'PUT',
    body: form,
    onSuccess: updateRecipeSuccess,
  }))
}

export const updateNotes = (id, notes) => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${id}/notes`,
    method: 'POST',
    body: { recipeId: id, notes },
    onSuccess: updateNotesSuccess,
  }))
}

export const updateNotesSuccess = data => dispatch => {
  dispatch(setState('pages.recipePage.notes', data.notes))
}

export const getRecipeNotes = id => dispatch => {
  dispatch(fetchAction({
    url: `/api/recipes/${id}/notes`,
    method: 'GET',
    onSuccess: getRecipesNotesSuccess,
    onError: getRecipesNotesError,
  }))
}

export const getRecipesNotesSuccess = data => dispatch => {
  dispatch(setState('pages.recipePage.notes', data.notes))
  dispatch(setState('pages.recipePage.notesInput', ''))
}

export const getRecipesNotesError = () => dispatch => {
  dispatch(setState('pages.recipePage.notes', ''))
  dispatch(setState('pages.recipePage.notesInput', ''))
}

export const showEditableNotes = () => (dispatch, getState) => {
  dispatch(setState('pages.recipePage.notesInput', getState().app.pages.recipePage.notes))
  dispatch(setState('pages.recipePage.editableNotes', true))
}

export const hideEditableNotes = () => (dispatch) => {
  dispatch(setState('pages.recipePage.notesInput', ''))
  dispatch(setState('pages.recipePage.editableNotes', false))
}