import React from 'react'
import { shallow } from 'enzyme'
import { Link } from 'react-router-dom'

import Toolbar from '@material-ui/core/Toolbar'
import Button from '@material-ui/core/Button'

import { Header } from './Header'

describe('Header', function () {
  it('renders without crashing', () => {
    const header = shallow(<Header />)
    expect(header).toBeDefined()
  })

  describe('Content', function() {
    beforeEach(() => {
      this.instance = shallow(<Header />)
    })

    it('is a Toolbar', () => {
      expect(this.instance.is(Toolbar)).toBeTruthy()
      expect(this.instance.props().disableGutters).toBeTruthy()
    })

    it('contains a 4 Button', () => {
      expect(this.instance.find(Button).length).toBe(4)
    })

    it('contains a home Button', () => {
      let button = this.instance.find(Button).at(0)

      expect(button.props().to).toBe('/')
      expect(button.props().component).toBe(Link)
      expect(button.find(Button).at(0).children().text()).toBe('Home')
    })

    it('contains a recipes Button', () => {
      let button = this.instance.find(Button).at(1)

      expect(button.props().to).toBe('/recipes')
      expect(button.props().component).toBe(Link)
      expect(button.find(Button).at(0).children().text()).toBe('Recipes')
    })

    it('contains a weeks Button', () => {
      let button = this.instance.find(Button).at(2)

      expect(button.props().to).toBe('/weeks')
      expect(button.props().component).toBe(Link)
      expect(button.find(Button).at(0).children().text()).toBe('Weeks')
    })

    it('contains a cart Button', () => {
      let button = this.instance.find(Button).at(3)

      expect(button.props().to).toBe('/carts')
      expect(button.props().component).toBe(Link)
      expect(button.find(Button).at(0).children().text()).toBe('Carts')
    })
  })
})
