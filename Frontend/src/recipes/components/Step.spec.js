import React from 'react'
import { shallow } from 'enzyme'

import Paper from '@material-ui/core/Paper'
import Typography from '@material-ui/core/Typography'

import styles from 'src/recipes/components/Step.css'
import {Step} from 'src/recipes/components/Step'

describe('Step', function () {
  it('renders without crashing', () => {
    const step = shallow(<Step />)
    expect(step).toBeDefined()
  })

  describe('Content', function() {
    let number = 12
    let step = 'This a sample step'

    beforeEach(() => {
      this.instance = shallow(<Step number={number} step={step} />)
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

    it('contains a Typography in a Grid item to display the step number', () => {
      let gridContainer = this.instance.findWhere(node => node.props().container).at(0)
      let grid = gridContainer.findWhere(node => node.props().item).at(0)
      expect(grid).toBeDefined()
      expect(grid.props().sm).toBe(1)

      let typography = grid.find(Typography).at(0)
      expect(typography.props().variant).toBe('body2')
      expect(typography.props().align).toBe('center')
      expect(typography.props().className).toBe(styles.numberedList)
      expect(typography.children().text()).toBe(number.toString())
    })

    it('contains a Typography in a Grid item to display the step', () => {
      let gridContainer = this.instance.findWhere(node => node.props().container).at(0)
      let grid = gridContainer.findWhere(node => node.props().item).at(1)
      expect(grid).toBeDefined()
      expect(grid.props().sm).toBe(11)

      let typography = grid.find(Typography).at(0)
      expect(typography.props().variant).toBe('body2')
      expect(typography.children().text()).toBe(step)
    })
  })
})
