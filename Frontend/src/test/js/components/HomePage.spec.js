import React from 'react'
import { shallow } from 'enzyme'

import { HomePage } from 'app-components/HomePage'

describe('HomePage', function () {
  it('renders without crashing', () => {
    const home = shallow(<HomePage />)
    expect(home).toBeDefined()
  })
})
