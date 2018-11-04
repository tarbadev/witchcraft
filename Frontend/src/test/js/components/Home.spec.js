import React from 'react'
import { shallow } from 'enzyme'

import {Home} from 'app-components/Home'
import {RecipeList} from 'app-components/RecipeList'

describe('Home', function () {
  it('renders without crashing', () => {
    const home = shallow(<Home />)
    expect(home).toBeDefined()
  })

  describe('Content', function() {
    beforeEach(() => {
      this.instance = shallow(<Home />)
    })

    it('contains a list of recipes', () => {
      expect(this.instance.find(RecipeList).length).toBe(1)
    })
  })
})
