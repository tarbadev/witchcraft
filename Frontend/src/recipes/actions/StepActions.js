import { fetchAction } from 'src/app/WitchcraftMiddleware'

export const saveStepNote = (recipeId, stepId, note, onSuccess) =>
  fetchAction({
    url: `/api/recipes/${recipeId}/steps/${stepId}`,
    method: 'POST',
    body: { note },
    onSuccess,
  })