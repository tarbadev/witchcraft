import Modal from '@material-ui/core/Modal'
import Grid from '@material-ui/core/Grid'
import Paper from '@material-ui/core/Paper'
import React, { useState } from 'react'
import * as PropTypes from 'prop-types'
import { Button, TextField } from '@material-ui/core'

import './ExpressRecipeFormModal.css'
import { useAppContext } from 'src/StoreProvider'

export const ExpressRecipeFormModalContainer = ({
  isModalOpen,
  onAddRecipeClick,
  onClose,
}) => {
  const { state } = useAppContext()
  const [recipeName, setRecipeName] = useState(state.pages.weekPage.expressRecipeForm.recipeName)

  return <ExpressRecipeFormModal
    isModalOpen={isModalOpen}
    recipeName={recipeName}
    onAddRecipeClick={onAddRecipeClick}
    onRecipeNameChange={newRecipeName => setRecipeName(newRecipeName)}
    onClose={onClose} />
}

ExpressRecipeFormModalContainer.propTypes = {
  isModalOpen: PropTypes.bool,
  onAddRecipeClick: PropTypes.func,
  onClose: PropTypes.func,
}

export const ExpressRecipeFormModal = ({
  isModalOpen,
  recipeName,
  onRecipeNameChange,
  onAddRecipeClick,
  onClose,
}) => {
  return (
    <Modal
      data-name='express-recipe-form-modal'
      aria-labelledby="simple-modal-title"
      aria-describedby="simple-modal-description"
      open={isModalOpen}
      onClose={onClose}
      disableAutoFocus={true}>
      <Grid container justify='center' alignItems='center' direction='row'>
        <Grid item xs={4}>
          <Paper className='express-recipe-form__paper'>
            <TextField
              fullWidth
              className='express-recipe-form__recipe-name'
              onChange={(e) => onRecipeNameChange(e.target.value)}
              value={recipeName}
              label='Name'
              type='search' />
            <Button
              className='express-recipe-form__submit-button'
              onClick={() => onAddRecipeClick(recipeName)}
              variant='contained'
              href=''
              color='primary'>
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
  onClose: PropTypes.func,
}