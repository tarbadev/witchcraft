import React, { createContext, useContext, useReducer, useState } from 'react'
import { initialState, reducer } from './RootReducer'
import { applyMiddleware, fetchAction } from './WitchcraftMiddleware'
import PropTypes from 'prop-types'

export const useActions = (state, dispatch)  => ({
  fetch: data => dispatch(fetchAction(data)),
})

const StoreContext = createContext(initialState)

export const StoreProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState)
  const enhancedDispatch = applyMiddleware(dispatch)
  const [currentHeader, setCurrentHeader] = useState(state.currentPage)

  const actions = useActions(state, enhancedDispatch)

  return (
    <StoreContext.Provider value={{ state, dispatch: enhancedDispatch, actions, currentHeader, setCurrentHeader }}>
      {children}
    </StoreContext.Provider>
  )
}

export const useAppContext = () => useContext(StoreContext)

StoreProvider.propTypes = {
  children: PropTypes.node.isRequired,
}