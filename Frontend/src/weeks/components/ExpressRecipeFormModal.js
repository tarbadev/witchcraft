import Modal from '@material-ui/core/Modal'
import Grid from '@material-ui/core/Grid'
import Paper from '@material-ui/core/Paper'
import React from 'react'
import * as PropTypes from 'prop-types'
import { TextField } from '@material-ui/core'
import Button from '@material-ui/core/Button'

export const ExpressRecipeFormModal = ({
  isModalOpen,
  recipeName,
  onRecipeNameChange,
  onAddRecipeClick,
}) => {
  return (
    <Modal
      aria-labelledby="simple-modal-title"
      aria-describedby="simple-modal-description"
      open={isModalOpen}
      // onClose={closeModal}
      disableAutoFocus={true}>
      <Grid container justify='center' alignItems='center' direction='row'>
        <Grid item xs={4}>
          <Paper>
            <TextField
              fullWidth
              className='express-recipe-form__recipe-name'
              onChange={(e) => onRecipeNameChange(e.target.value)}
              value={recipeName}
              label='Lasagna'
              type='search' />
            <Button
              className='week-page__add-express-recipe__submit-button'
              onClick={() => onAddRecipeClick(recipeName)}>
              Add
            </Button>
          </Paper>
        </Grid>
      </Grid>
    </Modal>
  )
}

ExpressRecipeFormModal.propTypes = {
  isModalOpen: PropTypes.bool,
  recipeName: PropTypes.string,
  onRecipeNameChange: PropTypes.func,
  onAddRecipeClick: PropTypes.func,
}