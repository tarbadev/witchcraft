import React from 'react'
import { shallow } from 'enzyme'

import { HomePage } from './HomePage'

describe('HomePage', function () {
  it('renders without crashing', () => {
    const home = shallow(<HomePage />)
    expect(home).toBeDefined()
  })
})
