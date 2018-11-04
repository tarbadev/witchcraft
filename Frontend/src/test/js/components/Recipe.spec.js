import React from 'react'
import {shallow} from 'enzyme'

import styles from 'app-components/Recipe.css'
import { Recipe } from 'app-components/Recipe'

import promisedRecipeList from 'test-resources/recipeList.json'
const promisedRecipe = promisedRecipeList.recipes[0]

describe('Recipe', function() {
  describe('setFavorite', () => {
    it('calls setFavorite on FavoriteIcon click', () => {
      const toggleFavoriteSpy = jasmine.createSpy()
      const recipePage = shallow(<Recipe toggleFavorite={toggleFavoriteSpy} recipe={promisedRecipe} />)
      recipePage.find(`.${styles.favoriteButton}`).simulate('click')

      expect(toggleFavoriteSpy).toHaveBeenCalledWith(promisedRecipe.id, !promisedRecipe.favorite)
    })
  })

  describe('onModifyButtonClick', () => {
    it('calls history.push when Modify button clicked', () => {
      const pushSpy = jasmine.createSpy()
      const recipePage = shallow(<Recipe history={{push: pushSpy}} recipe={promisedRecipe} />)

      recipePage.find(`.${styles.modifyButton}`).simulate('click')

      expect(pushSpy).toHaveBeenCalledWith(`/recipes/${promisedRecipe.id}/edit`)
    })
  })

  describe('deleteRecipe', () => {
    it('calls deleteRecipe when Delete button clicked', () => {
      const deleteRecipeSpy = jasmine.createSpy()
      const recipePage = shallow(<Recipe deleteRecipe={deleteRecipeSpy} recipe={promisedRecipe} />)

      recipePage.find(`.${styles.deleteButton}`).simulate('click')

      expect(deleteRecipeSpy).toHaveBeenCalledWith(promisedRecipe.id)
    })
  })
})
