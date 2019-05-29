import React from 'react'
import { shallow } from 'enzyme'

import styles from './App.css'
import { App } from './App'
import { Header } from './Header'
import { Content } from './Content'

describe('App', function () {
  it('renders without crashing', () => {
    const app = shallow(<App />)
    expect(app).toBeDefined()
  })

  describe('Content', function () {
    beforeEach(() => {
      this.instance = shallow(<App />)
    })

    it('contains a Header in a Grid item', () => {
      let item = this.instance.findWhere(node => node.props().item).at(0)
      expect(item.props().xs).toBe(10)
      expect(item.props().className).toBe(styles.header)
      expect(item.find(Header).length).toBe(1)
    })

    it('contains a Content in a Grid item', () => {
      let item = this.instance.findWhere(node => node.props().item).at(1)
      expect(item.props().xs).toBe(10)
      expect(item.find(Content).length).toBe(1)
    })
  })
})
