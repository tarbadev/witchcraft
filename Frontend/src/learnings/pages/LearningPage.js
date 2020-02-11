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

const TabPanel = ({ title, ingredients, display, validateIngredient, ...props }) => {
  const ingredientLearnings = ingredients.map(ingredient => (
    <LearningIngredient key={ingredient.id} ingredient={ingredient} validateIngredient={validateIngredient} />
  ))

  return (
    <Grid container {...props}>
      <Grid item xs={12} style={{ margin: useTheme().spacing(1) }}>
        <Typography variant='h6'>{title}: {ingredients.length}</Typography>
      </Grid>
      {display && ingredientLearnings}
    </Grid>
  )
}

TabPanel.propTypes = {
  title: PropTypes.string,
  ingredients: PropTypes.array,
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
        display={currentTab === 0}
        validateIngredient={validateIngredient}
        title='Ingredients to validate'
        data-need-validation-container
      />
      <TabPanel
        ingredients={validatedIngredients}
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