import { initialState } from './app/RootReducer'
import * as StoreProvider from './app/components/StoreProvider'

export const mockAppContext = () => {
  const context = { state: initialState, dispatch: jest.fn(), setCurrentHeader: jest.fn() }

  jest
    .spyOn(StoreProvider, 'useAppContext')
    .mockImplementation(() => context)

  return context
}