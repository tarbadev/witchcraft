import React from 'react'
import { shallow } from 'enzyme'

import { RecipePage } from './RecipePage'

import promisedRecipeList from 'test-resources/recipeList.json'

const promisedRecipe = promisedRecipeList.recipes[0]

describe('RecipePage', function () {
  const toggleFavoriteSpy = jest.fn()
  describe('setFavorite', () => {
    it('calls setFavorite on FavoriteIcon click', () => {
      const recipePage = shallow(<RecipePage toggleFavorite={toggleFavoriteSpy} recipe={promisedRecipe} />)
      recipePage.find('.favoriteButton').simulate('click')

      expect(toggleFavoriteSpy).toHaveBeenCalledWith(promisedRecipe.id, !promisedRecipe.favorite)
    })
  })

  describe('onModifyButtonClick', () => {
    it('calls history.push when Modify button clicked', () => {
      const pushSpy = jest.fn()
      const recipePage = shallow(
        <RecipePage toggleFavorite={toggleFavoriteSpy} history={{ push: pushSpy }} recipe={promisedRecipe} />,
      )

      recipePage.find('.modifyButton').simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/recipes/${promisedRecipe.id}/edit`)
    })
  })

  describe('deleteRecipe', () => {
    it('calls deleteRecipe when Delete button clicked', () => {
      const deleteRecipeSpy = jest.fn()
      const recipePage = shallow(
        <RecipePage toggleFavorite={toggleFavoriteSpy} deleteRecipe={deleteRecipeSpy} recipe={promisedRecipe} />,
      )

      recipePage.find('.deleteButton').simulate('click')

      expect(deleteRecipeSpy).toHaveBeenCalledWith(promisedRecipe.id)
    })
  })

  describe('Notes', () => {
    describe('when editableNotes is false', () => {
      it('notes is a Typography', () => {
        const recipePage = shallow(<RecipePage recipe={promisedRecipe} notes={'Some Notes'} editableNotes={false} />)

        expect(recipePage.find('.notes-container__notes').length).toBe(1)
      })

      it('and notes are empty, displays a message', () => {
        const recipePage = shallow(<RecipePage recipe={promisedRecipe} editableNotes={false} />)

        expect(recipePage.find('.notes-container__notes').length).toBe(1)
        expect(recipePage.find('.notes-container__notes').render().text()).toBe('Add a note')
      })

      it('does not display update button', () => {
        const recipePage = shallow(<RecipePage recipe={promisedRecipe} editableNotes={false} />)

        expect(recipePage.find('.notes-container__update-notes-button').length).toBe(0)
      })
    })
    it('when editableNotes is true, notes is a TextField', () => {
      const recipePage = shallow(<RecipePage recipe={promisedRecipe} editableNotes={true} />)

      expect(recipePage.find('.notes-container__editable-notes').length).toBe(1)
    })

    it('Shows editing of notes when we click on notes', () => {
      const showEditableNotesSpy = jest.fn()
      const recipePage = shallow(<RecipePage
        recipe={promisedRecipe}
        showEditableNotes={showEditableNotesSpy}
        editableNotes={false}
        notes={'Some notes'}
      />)

      recipePage.find('.notes-container__notes-content').simulate('click')

      expect(showEditableNotesSpy).toHaveBeenCalled()
    })

    it('Hides editing of notes when we click outside of it', () => {
      const hideEditableNotesSpy = jest.fn()
      const recipePage = shallow(<RecipePage
        recipe={promisedRecipe}
        hideEditableNotes={hideEditableNotesSpy}
        editableNotes={true}
        notes={'Some notes'}
      />)

      recipePage.find('.notes-container__editable-notes').simulate('blur')

      expect(hideEditableNotesSpy).toHaveBeenCalled()
    })

    it('Persists current note in state', () => {
      const editNotesSpy = jest.fn()
      const recipePage = shallow(<RecipePage recipe={promisedRecipe} editNotes={editNotesSpy} editableNotes={true} />)

      recipePage.find('.notes-container__editable-notes').simulate('change', { target: { value: 'The cheese is good' } })

      expect(editNotesSpy).toHaveBeenCalledWith('recipePage.notesInput', 'The cheese is good')
    })

    it('Calls updateNotes when update notes button clicked', () => {
      const updateNotesSpy = jest.fn()
      const notesInput = 'Some new notes'
      const recipePage = shallow(<RecipePage recipe={promisedRecipe} editableNotes={true} notesInput={notesInput} updateNotes={updateNotesSpy} />)

      recipePage.find('.notes-container__update-notes-button').simulate('mousedown')

      expect(updateNotesSpy).toHaveBeenCalledWith(promisedRecipe.id, notesInput)
    })
  })
})
