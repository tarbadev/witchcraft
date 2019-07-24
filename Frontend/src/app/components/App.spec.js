import React from 'react'
import { shallow } from 'enzyme'

import { App } from './App'
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

    it('contains a Content in a Grid item', () => {
      let item = this.instance.findWhere(node => node.props().item).at(1)
      expect(item.props().xs).toBe(10)
      expect(item.find(Content).length).toBe(1)
    })
  })
})
