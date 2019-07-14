import React, { createContext, useContext, useReducer } from 'react'
import { initialState, reducer } from './RootReducer'
import { applyMiddleware, fetchAction } from './WitchcraftMiddleware'
import PropTypes from 'prop-types'

export const useActions = (state, dispatch)  => ({
  fetch: data => dispatch(fetchAction(data)),
  call: method => dispatch(method)
})

const StoreContext = createContext(initialState)

export const StoreProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState)
  const enhancedDispatch = applyMiddleware(dispatch)

  const actions = useActions(state, enhancedDispatch)

  return (
    <StoreContext.Provider value={{ state, dispatch: enhancedDispatch, actions }}>
      {children}
    </StoreContext.Provider>
  )
}

export const useAppContext = () => useContext(StoreContext)

StoreProvider.propTypes = {
  children: PropTypes.node.isRequired,
}