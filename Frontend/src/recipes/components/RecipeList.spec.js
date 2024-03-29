import React from 'react'
import { shallow } from 'enzyme'
import { Link } from 'react-router-dom'

import { RecipeList } from 'src/recipes/components/RecipeList'
import { RecipeCard } from 'src/recipes/components/RecipeCard'

import promisedRecipeList from 'test-resources/recipeList.json'

describe('RecipeList', function () {
  it('renders without crashing', () => {
    const recipeList = shallow(<RecipeList recipes={promisedRecipeList.recipes} />)
    expect(recipeList).toBeDefined()
  })

  describe('Content', function () {
    beforeEach(() => {
      this.instance = shallow(<RecipeList recipes={promisedRecipeList.recipes} />)
    })

    it('renders a RecipeCard for each recipe in a Grid', () => {
      let grids = this.instance.findWhere(node => node.props().item)
      expect(grids.length).toBe(promisedRecipeList.recipes.length)

      let index = 0
      grids.map((grid) => {
        expect(grid.props().xs).toBe(12)
        expect(grid.props().sm).toBe(4)
        expect(grid.props().md).toBe(3)
        expect(grid.props().lg).toBe(2)
        expect(grid.find(Link).length).toBe(1)

        let link = grid.find(Link).at(0)
        expect(link.props().to).toBe(`/recipes/${promisedRecipeList.recipes[index].id}`)
        expect(link.find(RecipeCard).length).toBe(1)

        index++
      })
    })

    it('renders a RecipeCard with an imgUrl prop', () => {
      expect(this.instance.find(RecipeCard).get(0).props.imgUrl).toBe(promisedRecipeList.recipes[0].imgUrl)
    })

    it('renders a RecipeCard with a title prop', () => {
      expect(this.instance.find(RecipeCard).get(0).props.title).toBe(promisedRecipeList.recipes[0].name)
    })
  })
})
