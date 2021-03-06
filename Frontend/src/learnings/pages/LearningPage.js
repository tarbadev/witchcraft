import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import Tabs from '@material-ui/core/Tabs'
import Tab from '@material-ui/core/Tab'
import { useTheme } from '@material-ui/core'
import Typography from '@material-ui/core/Typography'
import SwipeableViews from 'react-swipeable-views'
import { initialState } from 'src/app/RootReducer'
import { useAppContext } from 'src/app/components/StoreProvider'
import { LEARNING } from 'src/app/components/Header'
import { LearningIngredient } from 'src/learnings/components/LearningIngredient'
import { getIngredientsToValidate, validateIngredient } from 'src/learnings/actions/LearningActions'
import { useInfiniteScroll } from 'src/app/components/useInfiniteScroll'

export const LearningPage = () => {
  const { dispatch, setHeaderConfig } = useAppContext()
  const [ingredientsToValidate, setIngredientsToValidate] = useState([])
  const [currentTab, setCurrentTab] = useState(0)

  const loadLearningIngredients = () => dispatch(
    getIngredientsToValidate(ingredients => setIngredientsToValidate(ingredients)),
  )

  useEffect(() => setHeaderConfig({
    ...initialState.headerConfig,
    currentLink: LEARNING,
    title: LEARNING,
  }), [])
  useEffect(loadLearningIngredients, [])

  return <LearningDisplayPage
    ingredientsToValidate={ingredientsToValidate.filter(ingredient => !ingredient.valid)}
    validatedIngredients={ingredientsToValidate.filter(ingredient => ingredient.valid)}
    validateIngredient={(id, newIngredient) => dispatch(validateIngredient(id,
      newIngredient,
      loadLearningIngredients,
      loadLearningIngredients))}
    currentTab={currentTab}
    switchTab={newTab => setCurrentTab(newTab)}
  />
}

const MAX_ITEMS_TO_DISPLAY = 10

const TabPanel = ({ title, ingredients, display, validateIngredient, validNames, validDetails, ...props }) => {
  const [ingredientsToDisplay, setIngredientsToDisplay] = useState([])

  const fetchMoreListItems = () => {
    setIngredientsToDisplay(ingredients.slice(0, ingredientsToDisplay.length + MAX_ITEMS_TO_DISPLAY))
    setIsFetching(false)
  }

  const [isFetching, setIsFetching] = useInfiniteScroll(fetchMoreListItems)
  useEffect(() => setIngredientsToDisplay(ingredients.slice(0, MAX_ITEMS_TO_DISPLAY)), [ingredients])

  const ingredientLearnings = ingredientsToDisplay.map(ingredient => (
    <LearningIngredient
      key={ingredient.id}
      ingredient={ingredient}
      validateIngredient={validateIngredient}
      validNames={validNames}
      validDetails={validDetails}
    />
  ))

  return (
    <Grid container {...props}>
      <Grid item xs={12} style={{ margin: useTheme().spacing(1) }}>
        <Typography variant='h6'>{title}: {ingredients.length}</Typography>
      </Grid>
      {display && ingredientLearnings}
      {isFetching &&
      <Grid item xs={12} style={{ margin: useTheme().spacing(1) }}>
        Loading more ingredients...
      </Grid>
      }
    </Grid>
  )
}

TabPanel.propTypes = {
  title: PropTypes.string,
  ingredients: PropTypes.array,
  validNames: PropTypes.array,
  validDetails: PropTypes.array,
  display: PropTypes.bool,
  validateIngredient: PropTypes.func,
}

const LearningDisplayPage = ({
  ingredientsToValidate,
  validatedIngredients,
  validateIngredient,
  currentTab,
  switchTab,
}) => {
  const names = validatedIngredients.map(ingredient => ingredient.name)
  const validNames = names
    .filter((name, index) => name !== '' && names.indexOf(name) === index)
    .sort()
  const details = validatedIngredients.map(ingredient => ingredient.detail)
  const validDetails = details
    .filter((detail, index) => detail !== '' && details.indexOf(detail) === index)
    .sort()

  return <div>
    <Tabs
      value={currentTab}
      onChange={(e, newTab) => switchTab(newTab)}
      indicatorColor="primary"
      textColor="primary"
      centered
    >
      <Tab label="Needs validation" />
      <Tab label="Validated" data-tab-validated />
    </Tabs>
    <SwipeableViews
      index={currentTab}
      onChangeIndex={switchTab}
    >
      <TabPanel
        ingredients={ingredientsToValidate}
        validNames={validNames}
        validDetails={validDetails}
        display={currentTab === 0}
        validateIngredient={validateIngredient}
        title='Ingredients to validate'
        data-need-validation-container
      />
      <TabPanel
        ingredients={validatedIngredients}
        validNames={validNames}
        validDetails={validDetails}
        display={currentTab === 1}
        validateIngredient={validateIngredient}
        title='Validated ingredients'
        data-validated-container
      />
    </SwipeableViews>
  </div>
}

LearningDisplayPage.propTypes = {
  ingredientsToValidate: PropTypes.array,
  validatedIngredients: PropTypes.array,
  validateIngredient: PropTypes.func,
  currentTab: PropTypes.number,
  switchTab: PropTypes.func,
}