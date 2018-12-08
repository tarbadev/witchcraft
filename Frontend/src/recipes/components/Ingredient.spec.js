import React from 'react'
import { shallow } from 'enzyme'

import Paper from '@material-ui/core/Paper'
import Typography from '@material-ui/core/Typography'

import styles from 'src/recipes/components/Ingredient.css'
import { Ingredient } from 'src/recipes/components/Ingredient'

describe('Ingredient', function () {
  it('renders without crashing', () => {
    const ingredient = shallow(<Ingredient />)
    expect(ingredient).toBeDefined()
  })

  describe('Content', function () {
    let quantity = 12
    let unit = 'tbsp'
    let ingredient = 'This a sample ingredient'

    beforeEach(() => {
      this.instance = shallow(<Ingredient ingredient={ingredient} quantity={quantity} unit={unit} />)
    })

    it('is a Paper', () => {
      expect(this.instance.is(Paper)).toBeTruthy()
      expect(this.instance.props().elevation).toBe(1)
      expect(this.instance.props().className).toBe(styles.paper)
    })

    it('contains a Grid container', () => {
      let grid = this.instance.findWhere(node => node.props().container).at(0)
      expect(grid).toBeDefined()
    })

    it('contains a Typography in a Grid item to display the unit and quantity', () => {
      let gridContainer = this.instance.findWhere(node => node.props().container).at(0)
      let grid = gridContainer.findWhere(node => node.props().item).at(1)
      expect(grid).toBeDefined()
      expect(grid.props().sm).toBe(2)

      let typography = grid.find(Typography).at(0)
      expect(typography.props().variant).toBe('body2')
      expect(typography.props().align).toBe('right')
      expect(typography.props().className).toBe(styles.unit)
      expect(typography.children().text()).toBe(quantity + ' ' + unit)
    })

    it('contains a Typography in a Grid item to display the ingredient', () => {
      let gridContainer = this.instance.findWhere(node => node.props().container).at(0)
      let grid = gridContainer.findWhere(node => node.props().item).at(0)
      expect(grid).toBeDefined()
      expect(grid.props().sm).toBe(10)

      let typography = grid.find(Typography).at(0)
      expect(typography.props().variant).toBe('body2')
      expect(typography.props().className).toBe(styles.ingredient)
      expect(typography.children().text()).toBe(ingredient)
    })
  })
})
