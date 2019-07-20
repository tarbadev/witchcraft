import { setState } from 'src/RootReducer'
import { fetchAction } from 'src/WitchcraftMiddleware'

export const getRecipe = (id, onSuccess, onError) =>
  fetchAction({
    url: `/api/recipes/${id}`,
    method: 'GET',
    onSuccess,
    onError,
  })

export const setFavorite = (id, isFavorite, onSuccess) =>
  fetchAction({
    url: `/api/recipes/${id}`,
    method: 'PATCH',
    body: {
      favorite: isFavorite,
    },
    onSuccess,
  })

export const deleteRecipe = (id, onSuccess, onError) =>
  fetchAction({
    url: `/api/recipes/${id}`,
    method: 'DELETE',
    onSuccess,
    onError,
  })

export const updateRecipe = (form, onSuccess) =>
  fetchAction({
    url: `/api/recipes/${form.id}/update`,
    method: 'PUT',
    body: form,
    onSuccess: onSuccess,
  })

export const updateNotes = (id, notes, onSuccess) =>
  fetchAction({
    url: `/api/recipes/${id}/notes`,
    method: 'POST',
    body: { recipeId: id, notes },
    onSuccess,
  })

export const getRecipeNotes = (id, onSuccess) =>
  fetchAction({
    url: `/api/recipes/${id}/notes`,
    method: 'GET',
    onSuccess
  })