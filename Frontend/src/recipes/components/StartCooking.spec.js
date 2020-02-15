import React from 'react'
import { mount } from 'enzyme'
import promisedRecipeList from 'test-resources/recipeList.json'
import { mockAppContext } from 'src/testUtils'
import { StartCooking } from 'src/recipes/components/StartCooking'

describe('Start Cooking', () => {
  const recipe = promisedRecipeList.recipes[0]
  let startCooking

  beforeEach(() => {
    mockAppContext()

    startCooking = mount(<StartCooking open={true} recipe={recipe} />)
  })

  it('displays a title', () => {
    expect(startCooking.find('[data-start-cooking-title]').at(0).text()).toEqual(recipe.name)
  })

  it('displays the first step', () => {
    expect(startCooking.find('[data-start-cooking-step]').at(0).text()).toEqual(recipe.steps[0].name)
  })

  it('displays empty text if no step available', () => {
    startCooking = mount(<StartCooking open={true} recipe={{ ...recipe, steps: [] }} />)
    expect(startCooking.find('[data-start-cooking-step]').at(0).text()).toEqual('')
    expect(startCooking.find('[data-start-cooking-step-note]').at(0).text()).toEqual('')
  })

  it('displays the first step\'s note', () => {
    expect(startCooking.find('[data-start-cooking-step-note]').at(0).text()).toEqual(`Note: ${recipe.steps[0].note}`)
  })

  it('displays the ingredients', () => {
    expect(startCooking.find('[data-ingredient-container] [data-name]')).toHaveLength(recipe.ingredients.length)
    expect(startCooking.find('[data-ingredient-container] [data-quantity]')).toHaveLength(recipe.ingredients.length)
  })
})