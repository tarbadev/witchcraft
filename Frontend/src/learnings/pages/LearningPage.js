import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import { useAppContext } from 'src/app/components/StoreProvider'
import { getIngredientsToValidate, validateIngredient } from 'src/learnings/actions/LearningActions'
import { initialState } from 'src/app/RootReducer'
import { LEARNING } from 'src/app/components/Header'
import TextField from '@material-ui/core/TextField'
import Select from '@material-ui/core/Select'
import MenuItem from '@material-ui/core/MenuItem'
import Button from '@material-ui/core/Button'
import Paper from '@material-ui/core/Paper'
import Tabs from '@material-ui/core/Tabs'
import Tab from '@material-ui/core/Tab'
import SwipeableViews from 'react-swipeable-views'
import { useTheme } from '@material-ui/core'
import Typography from '@material-ui/core/Typography'

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

const LearningIngredient = ({ ingredient, validateIngredient }) => {
  const [editName, setEditName] = useState(ingredient.name)
  const [editQuantity, setEditQuantity] = useState(ingredient.quantity)
  const [editUnit, setEditUnit] = useState(ingredient.unit)
  const [editDetail, setEditDetail] = useState(ingredient.detail)
  const [editLanguage, setEditLanguage] = useState(ingredient.language)

  return <LearningIngredientDisplay
    line={ingredient.line}
    name={editName}
    updateName={newName => setEditName(newName)}
    quantity={editQuantity}
    updateQuantity={newQuantity => setEditQuantity(newQuantity)}
    unit={editUnit}
    updateUnit={newUnit => setEditUnit(newUnit)}
    detail={editDetail}
    updateDetail={newDetail => setEditDetail(newDetail)}
    language={editLanguage}
    updateLanguage={newLanguage => setEditLanguage(newLanguage)}
    validateIngredient={() => validateIngredient(
      ingredient.id,
      { name: editName, quantity: editQuantity, unit: editUnit, language: editLanguage, detail: editDetail },
    )}
  />
}

LearningIngredient.propTypes = {
  ingredient: PropTypes.object,
  validateIngredient: PropTypes.func,
}

const LearningIngredientDisplay = ({
  line,
  name,
  updateName,
  quantity,
  updateQuantity,
  unit,
  updateUnit,
  detail,
  updateDetail,
  language,
  updateLanguage,
  validateIngredient,
}) => {
  return (
    <Grid item xs={12} style={{ margin: useTheme().spacing(1) }}>
      <Paper style={{ padding: 10 }}>
        <Grid container spacing={1} data-ingredient alignItems='flex-end'>
          <Grid item xs={12}>
            <TextField
              label='Line'
              value={line}
              fullWidth
              disabled
              data-ingredient-line />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              label='Name'
              value={name}
              fullWidth
              data-ingredient-name
              onChange={({ target }) => updateName(target.value)} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              label='Detail'
              value={detail}
              fullWidth
              data-ingredient-detail
              onChange={({ target }) => updateDetail(target.value)} />
          </Grid>
          <Grid item xs={4} sm>
            <TextField
              label='Quantity'
              value={quantity}
              fullWidth
              data-ingredient-quantity
              onChange={({ target }) => updateQuantity(target.value)} />
          </Grid>
          <Grid item xs={4} sm>
            <TextField
              label='Unit'
              value={unit}
              fullWidth
              data-ingredient-unit
              onChange={({ target }) => updateUnit(target.value)} />
          </Grid>
          <Grid item xs={4} sm>
            <Select
              label='Language'
              value={language}
              fullWidth
              data-ingredient-language
              onChange={({ target }) => updateLanguage(target.value)}>
              <MenuItem value='ENGLISH'>English</MenuItem>
              <MenuItem value='FRENCH'>French</MenuItem>
            </Select>
          </Grid>
          <Grid item xs={12} sm>
            <Button
              variant='contained'
              color='primary'
              style={{ float: 'right' }}
              data-ingredient-validate
              onClick={validateIngredient}
              fullWidth
            >
              Validate
            </Button>
          </Grid>
        </Grid>
      </Paper>
    </Grid>
  )
}

LearningIngredientDisplay.propTypes = {
  line: PropTypes.string,
  name: PropTypes.string,
  updateName: PropTypes.func,
  quantity: PropTypes.number,
  updateQuantity: PropTypes.func,
  unit: PropTypes.string,
  updateUnit: PropTypes.func,
  detail: PropTypes.string,
  updateDetail: PropTypes.func,
  language: PropTypes.string,
  updateLanguage: PropTypes.func,
  validateIngredient: PropTypes.func,
}