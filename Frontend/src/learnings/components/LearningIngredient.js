import React, { useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import { Autocomplete } from '@material-ui/lab'
import { useTheme } from '@material-ui/core'
import Paper from '@material-ui/core/Paper'
import TextField from '@material-ui/core/TextField'
import Select from '@material-ui/core/Select'
import MenuItem from '@material-ui/core/MenuItem'
import Button from '@material-ui/core/Button'

export const LearningIngredient = ({ ingredient, validateIngredient, validNames, validDetails }) => {
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
    validNames={validNames.filter(validName => validName.includes(editName))}
    validDetails={validDetails.filter(validDetail => validDetail.includes(editDetail))}
  />
}

LearningIngredient.propTypes = {
  ingredient: PropTypes.object,
  validateIngredient: PropTypes.func,
  validNames: PropTypes.array,
  validDetails: PropTypes.array,
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
  validNames,
  validDetails,
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
            <Autocomplete
              freeSolo
              value={name}
              options={validNames}
              onChange={({ target }) => updateName(validNames[target.value])}
              renderInput={params => (
                <TextField
                  {...params}
                  label='Name'
                  fullWidth
                  data-ingredient-name
                  onChange={({ target }) => updateName(target.value)} />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <Autocomplete
              freeSolo
              value={detail}
              options={validDetails}
              onChange={({ target }) => updateDetail(validDetails[target.value])}
              renderInput={params => (
                <TextField
                  {...params}
                  label='Detail'
                  fullWidth
                  data-ingredient-detail
                  onChange={({ target }) => updateDetail(target.value)} />
              )}
            />
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
  validNames: PropTypes.array,
  validDetails: PropTypes.array,
}