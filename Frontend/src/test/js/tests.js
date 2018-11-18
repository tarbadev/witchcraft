import Enzyme from 'enzyme'
import Adapter from 'enzyme-adapter-react-16'
import fetch from 'node-fetch'
import fetchMock from 'fetch-mock'
import 'babel-polyfill'
import 'jest-enzyme'

Enzyme.configure({ adapter: new Adapter() })

global.fetch = fetch

beforeEach(() => {
  fetchMock.reset()
})
