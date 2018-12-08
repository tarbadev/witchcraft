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
})
