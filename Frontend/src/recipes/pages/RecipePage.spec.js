import React from 'react'
import { mount } from 'enzyme'

import { RecipePageContainer } from './RecipePage'

import promisedRecipeList from 'test-resources/recipeList.json'
import { mockAppContext } from 'src/testUtils'
import * as RecipeActions from '../actions/RecipeActions'

const promisedRecipe = promisedRecipeList.recipes[0]

describe('RecipePageContainer', () => {
  it('renders without crashing and loads the recipe', () => {
    const context = mockAppContext()
    const id = 2

    const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)

    expect(recipePageContainer).toBeDefined()
    expect(context.dispatch).toHaveBeenNthCalledWith(1, RecipeActions.getRecipe(id, expect.any(Function)))
  })

  it('dispatches setFavorite on FavoriteIcon click', () => {
    const context = mockAppContext()
    const id = promisedRecipe.id

    jest
      .spyOn(RecipeActions, 'getRecipe')
      .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

    const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)
    recipePageContainer.find('.favoriteButton button').simulate('click')

    expect(context.dispatch)
      .toHaveBeenLastCalledWith(RecipeActions.setFavorite(id, !promisedRecipe.favorite, expect.any(Function)))
  })

  it('dispatches history.push when Modify button clicked', () => {
    mockAppContext()
    const pushSpy = jest.fn()
    const id = promisedRecipe.id

    jest
      .spyOn(RecipeActions, 'getRecipe')
      .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

    const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} history={{ push: pushSpy }} />)
    recipePageContainer.find('.modifyButton button').simulate('click')

    expect(pushSpy).toHaveBeenCalledWith(`/recipes/${promisedRecipe.id}/edit`)
  })

  describe('delete', () => {
    it('displays confirmation dialog when delete button is clicked', () => {
      mockAppContext()
      const id = promisedRecipe.id

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)

      expect(recipePageContainer.find('[data-confirm-delete]')).toHaveLength(0)

      recipePageContainer.find('.deleteButton button').simulate('click')

      expect(recipePageContainer.find('[data-confirm-delete-title]')).toHaveLength(3)
    })

    it('dispatches deleteRecipe when confirm Delete button clicked', () => {
      const context = mockAppContext()
      const id = promisedRecipe.id

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)
      recipePageContainer.find('.deleteButton button').simulate('click')
      recipePageContainer.find('[data-confirm-delete-button] button').simulate('click')

      expect(context.dispatch)
        .toHaveBeenLastCalledWith(RecipeActions.deleteRecipe(id, expect.any(Function), expect.any(Function)))
    })

    it('displays circular progress when confirm delete button is clicked', () => {
      mockAppContext()
      const id = promisedRecipe.id

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)

      expect(recipePageContainer.find('.circularProgress')).toHaveLength(0)

      recipePageContainer.find('.deleteButton button').simulate('click')
      recipePageContainer.find('[data-confirm-delete-button] button').simulate('click')

      expect(recipePageContainer.find('.circularProgress')).toHaveLength(3)
    })

    it('Hides CircularProgress call is successful', () => {
      mockAppContext()
      const id = promisedRecipe.id

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(
        <RecipePageContainer
          match={{ params: { id } }}
          history={{ push: jest.fn() }} />)

      expect(recipePageContainer.find('.circularProgress')).toHaveLength(0)

      jest
        .spyOn(RecipeActions, 'deleteRecipe')
        .mockImplementation((id, onSuccess) => onSuccess())

      recipePageContainer.find('.deleteButton button').simulate('click')
      recipePageContainer.find('[data-confirm-delete-button] button').simulate('click')
      expect(recipePageContainer.find('.circularProgress')).toHaveLength(0)
    })

    it('redirects to recipes page when delete succeeds', () => {
      mockAppContext()
      const id = promisedRecipe.id
      const pushSpy = jest.fn()

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      jest
        .spyOn(RecipeActions, 'deleteRecipe')
        .mockImplementation((id, onSuccess) => onSuccess())

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} history={{ push: pushSpy }} />)
      recipePageContainer.find('.deleteButton button').simulate('click')
      recipePageContainer.find('[data-confirm-delete-button] button').simulate('click')

      expect(pushSpy).toHaveBeenLastCalledWith('/recipes')
    })
  })

  describe('Notes', () => {
    it('loads the notes when mounting the object', () => {
      const context = mockAppContext()

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      mount(<RecipePageContainer match={{ params: { id: promisedRecipe.id } }} />)

      expect(context.dispatch)
        .toHaveBeenLastCalledWith(RecipeActions.getRecipeNotes(promisedRecipe.id, expect.any(Function)))
    })

    it('displays a message when notes are empty', () => {
      mockAppContext()

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id: promisedRecipe.id } }} />)

      expect(recipePageContainer.find('.notes-container__empty-value').at(0).text()).toEqual('Add a note')
    })

    it('displays the notes editable mode on click', () => {
      mockAppContext()
      const notes = 'Some notes about it'

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      jest
        .spyOn(RecipeActions, 'getRecipeNotes')
        .mockImplementation((id, onSuccess) => onSuccess({ notes }))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id: promisedRecipe.id } }} />)

      expect(recipePageContainer.find('.notes-container__editable-value')).toHaveLength(0)
      expect(recipePageContainer.find('.notes-container__update-value-button button')).toHaveLength(0)

      recipePageContainer.find('.notes-container__value-content').at(0).simulate('click')

      expect(recipePageContainer.find('.notes-container__editable-value textarea')).toHaveLength(2)
      expect(recipePageContainer.find('.notes-container__editable-value textarea').at(0).text()).toEqual(notes)
      expect(recipePageContainer.find('.notes-container__update-value-button button')).toHaveLength(1)
    })

    it('Hides the editable notes on click away', () => {
      mockAppContext()

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id: promisedRecipe.id } }} />)

      recipePageContainer.find('.notes-container__empty-value').at(0).simulate('click')
      expect(recipePageContainer.find('.notes-container__editable-value textarea')).toHaveLength(2)

      recipePageContainer.find('.notes-container__editable-value textarea').at(0).simulate('blur')
      expect(recipePageContainer.find('.notes-container__editable-value')).toHaveLength(0)
    })

    it('Dispatches an update notes call', () => {
      const context = mockAppContext()
      const notes = 'The cheese is good'

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      jest
        .spyOn(RecipeActions, 'getRecipeNotes')
        .mockImplementation((id, onSuccess) => onSuccess({ notes }))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id: promisedRecipe.id } }} />)

      recipePageContainer.find('.notes-container__value-content').at(0).simulate('click')
      recipePageContainer.find('.notes-container__update-value-button button').simulate('mousedown')

      expect(context.dispatch)
        .toHaveBeenLastCalledWith(RecipeActions.updateNotes(promisedRecipe.id, notes, expect.any(Function)))
    })

    it('Updates the notes when an update notes call is successful', () => {
      mockAppContext()
      const newNotes = 'Some new notes'

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      jest
        .spyOn(RecipeActions, 'updateNotes')
        .mockImplementation((id, notes, onSuccess) => onSuccess({ notes: newNotes }))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id: promisedRecipe.id } }} />)

      recipePageContainer.find('.notes-container__empty-value').at(0).simulate('click')
      recipePageContainer.find('.notes-container__update-value-button button').simulate('mousedown')
      recipePageContainer.find('.notes-container__editable-value textarea').at(0).simulate('blur')

      expect(recipePageContainer.find('.notes-container__value-content').at(0).text()).toEqual(newNotes)
    })

    it('Updates the notes text on change', () => {
      mockAppContext()
      const notes = 'The cheese is good'
      const newNotes = 'The cheese is better when grated'

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      jest
        .spyOn(RecipeActions, 'getRecipeNotes')
        .mockImplementation((id, onSuccess) => onSuccess({ notes }))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id: promisedRecipe.id } }} />)

      recipePageContainer.find('.notes-container__value-content').at(0).simulate('click')
      expect(recipePageContainer.find('.notes-container__editable-value textarea').at(0).text()).toEqual(notes)

      recipePageContainer.find('.notes-container__editable-value textarea')
        .at(0)
        .simulate('change', { target: { value: newNotes } })

      expect(recipePageContainer.find('.notes-container__editable-value textarea').at(0).text()).toEqual(newNotes)
    })
  })

  describe('on Ingredient deletion', () => {
    it('displays confirmation dialog when delete button is clicked', () => {
      const context = mockAppContext()
      const id = promisedRecipe.id

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementationOnce((id, onSuccess) => onSuccess(promisedRecipe))
      jest
        .spyOn(RecipeActions, 'deleteIngredient')
        .mockImplementation((id, ingredientId, onSuccess) => onSuccess())

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)

      recipePageContainer.find('.ingredient_0 [data-ingredient-container]').at(0).simulate('click')
      recipePageContainer.find('.ingredient_0 [data-edit-delete] button').simulate('click')
      recipePageContainer.find('.ingredient_0 [data-edit-confirm-delete] button').simulate('click')

      setTimeout(done => {
        expect(context.dispatch).toHaveBeenLastCalledWith(RecipeActions.getRecipe(id, expect.any(Function)))
        done()
      }, 500)
    })
  })

  describe('Converter', () => {
    describe('on Converter click', () => {
      it('displays the Converter', () => {
        mockAppContext()
        const id = promisedRecipe.id

        const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)

        expect(recipePageContainer.find('[data-converter-container]').at(0).prop('open')).toBeFalsy()

        recipePageContainer.find('[data-open-converter] button').at(0).simulate('click')

        expect(recipePageContainer.find('[data-converter-container]').at(0).prop('open')).toBeTruthy()
      })
    })

    describe('on Close button click', () => {
      it('closes the Converter', () => {
        mockAppContext()
        const id = promisedRecipe.id

        const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)

        recipePageContainer.find('[data-open-converter] button').at(0).simulate('click')
        expect(recipePageContainer.find('[data-converter-container]').at(0).prop('open')).toBeTruthy()

        recipePageContainer.find('[data-close-converter] button').at(0).simulate('click')
        expect(recipePageContainer.find('[data-converter-container]').at(0).prop('open')).toBeFalsy()
      })
    })
  })

  describe('On portions update', () => {
    it('updates the portions value', () => {
      mockAppContext()
      const id = promisedRecipe.id
      const newPortions = '8'
      const recipe = { ...promisedRecipe, portions: newPortions }

      jest
        .spyOn(RecipeActions, 'updatePortions')
        .mockImplementation((id, portions, onSuccess) => onSuccess(recipe))

      const recipePageContainer = mount(<RecipePageContainer match={{ params: { id } }} />)

      expect(recipePageContainer.find('[data-portions-value]').text()).toEqual(promisedRecipe.portions)

      recipePageContainer.find('[data-portions-container]').at(0).simulate('click')
      recipePageContainer.find('[data-edit-portions] input').at(0).simulate('change', { target: { value: newPortions } })
      recipePageContainer.find('[data-save-portions] button').simulate('mousedown')

      expect(recipePageContainer.find('[data-portions-value]').text()).toEqual(newPortions)
    })
  })
})