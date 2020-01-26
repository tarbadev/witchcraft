import React from 'react'
import { mount } from 'enzyme'

import { RecipePage } from './RecipePage'

import promisedRecipeList from 'test-resources/recipeList.json'
import { mockAppContext } from 'src/testUtils'
import * as RecipeActions from '../actions/RecipeActions'
import { Header } from 'src/app/components/Header'
import { BrowserRouter } from 'react-router-dom'

const promisedRecipe = promisedRecipeList.recipes[0]

describe('RecipePage', () => {
  it('renders without crashing and loads the recipe', () => {
    const context = mockAppContext()
    const id = 2

    const recipePageContainer = mount(<RecipePage match={{ params: { id } }} />)

    expect(recipePageContainer).toBeDefined()
    expect(context.dispatch).toHaveBeenNthCalledWith(1, RecipeActions.getRecipe(id, expect.any(Function)))
  })

  it('sets the header config', () => {
    const context = mockAppContext()
    const id = 2

    mount(<RecipePage match={{ params: { id } }} />)

    expect(context.setHeaderConfig).toHaveBeenCalled()
  })

  it('dispatches setFavorite on FavoriteIcon click', () => {
    const context = mockAppContext()
    const id = promisedRecipe.id

    jest
      .spyOn(RecipeActions, 'getRecipe')
      .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

    const recipePageContainer = mount(<RecipePage match={{ params: { id } }} />)
    recipePageContainer.find('[data-toggle-favorite-button] button').simulate('click')

    expect(context.dispatch)
      .toHaveBeenLastCalledWith(RecipeActions.setFavorite(id, !promisedRecipe.favorite, expect.any(Function)))
  })

  it('dispatches history.push when Modify button clicked', () => {
    const context = mockAppContext()
    const pushSpy = jest.fn()
    const id = promisedRecipe.id

    jest
      .spyOn(RecipeActions, 'getRecipe')
      .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

    mount(<RecipePage match={{ params: { id } }} history={{ push: pushSpy }} />)

    const headerConfig = context.setHeaderConfig.mock.calls[0][0]
    mockAppContext({ headerConfig })

    const recipePageContainer = mount(<BrowserRouter>
      <Header />
      <RecipePage match={{ params: { id } }} history={{ push: pushSpy }} />
    </BrowserRouter>)
    recipePageContainer.find('[data-edit-button]').at(0).simulate('click')

    expect(pushSpy).toHaveBeenCalledWith(`/recipes/${promisedRecipe.id}/edit`)
  })

  describe('delete', () => {
    let recipePageContainer
    let fullPageContainer
    let pushSpy
    let context

    const id = promisedRecipe.id

    beforeEach(() => {
      pushSpy = jest.fn()
      context = mockAppContext()

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      recipePageContainer = mount(<RecipePage match={{ params: { id } }} history={{ push: pushSpy }} />)

      const headerConfig = context.setHeaderConfig.mock.calls[0][0]
      context = mockAppContext({ headerConfig })

      fullPageContainer = mount(<BrowserRouter>
        <Header />
        <RecipePage match={{ params: { id } }} history={{ push: pushSpy }} />
      </BrowserRouter>)

    })

    it('displays confirmation dialog when delete button is clicked', () => {
      expect(recipePageContainer.find('[data-confirm-delete-title]')).toHaveLength(0)

      fullPageContainer.find('[data-delete-button]').at(0).simulate('click')
      recipePageContainer.update()

      expect(recipePageContainer.find('[data-confirm-delete-title]')).toHaveLength(3)
    })

    it('dispatches deleteRecipe when confirm Delete button clicked', () => {
      fullPageContainer.find('[data-delete-button]').at(0).simulate('click')
      recipePageContainer.update()

      recipePageContainer.find('[data-confirm-delete-button] button').simulate('click')

      expect(context.dispatch)
        .toHaveBeenLastCalledWith(RecipeActions.deleteRecipe(id, expect.any(Function), expect.any(Function)))
    })

    it('redirects to recipes page when delete succeeds', () => {
      jest
        .spyOn(RecipeActions, 'deleteRecipe')
        .mockImplementation((id, onSuccess) => onSuccess())

      fullPageContainer.find('[data-delete-button]').at(0).simulate('click')
      recipePageContainer.update()

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

      mount(<RecipePage match={{ params: { id: promisedRecipe.id } }} />)

      expect(context.dispatch)
        .toHaveBeenLastCalledWith(RecipeActions.getRecipeNotes(promisedRecipe.id, expect.any(Function)))
    })

    it('displays a message when notes are empty', () => {
      mockAppContext()

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePage match={{ params: { id: promisedRecipe.id } }} />)

      expect(recipePageContainer.find('[data-display-value]').at(0).text()).toEqual('Add a note')
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

      const recipePageContainer = mount(<RecipePage match={{ params: { id: promisedRecipe.id } }} />)

      expect(recipePageContainer.find('[data-edit-value]')).toHaveLength(0)
      expect(recipePageContainer.find('.notes-container__update-value-button button')).toHaveLength(0)

      recipePageContainer.find('[data-display-value]').at(0).simulate('click')

      expect(recipePageContainer.find('[data-edit-value] textarea')).toHaveLength(2)
      expect(recipePageContainer.find('[data-edit-value] textarea').at(0).text()).toEqual(notes)
      expect(recipePageContainer.find('.notes-container__update-value-button button')).toHaveLength(1)
    })

    it('Hides the editable notes on click away', () => {
      mockAppContext()

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePage match={{ params: { id: promisedRecipe.id } }} />)

      recipePageContainer.find('[data-display-value]').at(0).simulate('click')
      expect(recipePageContainer.find('[data-edit-value] textarea')).toHaveLength(2)

      recipePageContainer.find('[data-edit-value] textarea').at(0).simulate('blur')
      expect(recipePageContainer.find('[data-edit-value]')).toHaveLength(0)
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

      const recipePageContainer = mount(<RecipePage match={{ params: { id: promisedRecipe.id } }} />)

      recipePageContainer.find('[data-display-value]').at(0).simulate('click')
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

      const recipePageContainer = mount(<RecipePage match={{ params: { id: promisedRecipe.id } }} />)

      recipePageContainer.find('[data-display-value]').at(0).simulate('click')
      recipePageContainer.find('.notes-container__update-value-button button').simulate('mousedown')
      recipePageContainer.find('[data-edit-value] textarea').at(0).simulate('blur')

      expect(recipePageContainer.find('[data-display-value]').at(0).text()).toEqual(newNotes)
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

      const recipePageContainer = mount(<RecipePage match={{ params: { id: promisedRecipe.id } }} />)

      recipePageContainer.find('[data-display-value]').at(0).simulate('click')
      expect(recipePageContainer.find('[data-edit-value] textarea').at(0).text()).toEqual(notes)

      recipePageContainer.find('[data-edit-value] textarea')
        .at(0)
        .simulate('change', { target: { value: newNotes } })

      expect(recipePageContainer.find('[data-edit-value] textarea').at(0).text()).toEqual(newNotes)
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

      const recipePageContainer = mount(<RecipePage match={{ params: { id } }} />)

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
    let recipePageContainer
    let fullPageContainer
    let pushSpy
    let context

    const id = promisedRecipe.id

    beforeEach(() => {
      pushSpy = jest.fn()
      context = mockAppContext()

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      recipePageContainer = mount(<RecipePage match={{ params: { id } }} history={{ push: pushSpy }} />)

      const headerConfig = context.setHeaderConfig.mock.calls[0][0]
      context = mockAppContext({ headerConfig })

      fullPageContainer = mount(<BrowserRouter>
        <Header />
        <RecipePage match={{ params: { id } }} history={{ push: pushSpy }} />
      </BrowserRouter>)
    })

    describe('on Converter click', () => {
      it('displays the Converter', () => {
        expect(recipePageContainer.find('[data-converter-container]').at(0).prop('open')).toBeFalsy()

        fullPageContainer.find('[data-open-converter]').at(0).simulate('click')
        recipePageContainer.update()

        expect(recipePageContainer.find('[data-converter-container]').at(0).prop('open')).toBeTruthy()
      })
    })

    describe('on Close button click', () => {
      it('closes the Converter', () => {
        fullPageContainer.find('[data-open-converter]').at(0).simulate('click')
        recipePageContainer.update()
        expect(recipePageContainer.find('[data-converter-container]').at(0).prop('open')).toBeTruthy()

        recipePageContainer.find('[data-close-converter]').at(0).simulate('click')
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
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      jest
        .spyOn(RecipeActions, 'updatePortions')
        .mockImplementation((id, portions, onSuccess) => onSuccess(recipe))

      const recipePageContainer = mount(<RecipePage match={{ params: { id } }} />)

      expect(recipePageContainer.find('[data-portions-value]').text()).toEqual(promisedRecipe.portions)

      recipePageContainer.find('[data-portions-container]').at(0).simulate('click')
      recipePageContainer.find('[data-edit-portions] input')
        .at(0)
        .simulate('change', { target: { value: newPortions } })
      recipePageContainer.find('[data-save-portions] button').simulate('mousedown')

      expect(recipePageContainer.find('[data-portions-value]').text()).toEqual(newPortions)
    })
  })

  describe('On portions up', () => {
    it('updates the portions value and the ingredient\'s quantity', () => {
      mockAppContext()
      const id = promisedRecipe.id

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePage match={{ params: { id } }} />)

      expect(recipePageContainer.find('[data-portions-value]').text()).toEqual(promisedRecipe.portions)
      expect(recipePageContainer.find('.ingredient_0 [data-quantity]').text()).toEqual('1 cup')

      recipePageContainer.find('[data-portions-button-up]').at(0).simulate('click')
      expect(recipePageContainer.find('[data-portions-value]').text()).toEqual(`${Number(promisedRecipe.portions) + 1}`)
      expect(recipePageContainer.find('.ingredient_0 [data-quantity]').text()).toEqual('1.5 cup')
    })
  })

  describe('On portions down', () => {
    it('updates the portions value and the ingredient\'s quantity', () => {
      mockAppContext()
      const id = promisedRecipe.id

      jest
        .spyOn(RecipeActions, 'getRecipe')
        .mockImplementation((id, onSuccess) => onSuccess(promisedRecipe))

      const recipePageContainer = mount(<RecipePage match={{ params: { id } }} />)

      expect(recipePageContainer.find('[data-portions-value]').text()).toEqual(promisedRecipe.portions)
      expect(recipePageContainer.find('.ingredient_0 [data-quantity]').text()).toEqual('1 cup')

      recipePageContainer.find('[data-portions-button-down]').at(0).simulate('click')
      expect(recipePageContainer.find('[data-portions-value]').text()).toEqual(`${Number(promisedRecipe.portions) - 1}`)
      expect(recipePageContainer.find('.ingredient_0 [data-quantity]').text()).toEqual('0.5 cup')
    })
  })
})