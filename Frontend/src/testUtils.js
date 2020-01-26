import { initialState } from './app/RootReducer'
import * as StoreProvider from './app/components/StoreProvider'

export const mockAppContext = ({ headerConfig = {} } = {}) => {
  const context = { state: initialState, dispatch: jest.fn(), setHeaderConfig: jest.fn(), headerConfig }

  jest
    .spyOn(StoreProvider, 'useAppContext')
    .mockImplementation(() => context)

  return context
}