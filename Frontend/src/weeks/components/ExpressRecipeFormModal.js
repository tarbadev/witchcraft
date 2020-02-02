import React, { useState } from 'react'
import * as PropTypes from 'prop-types'
import { Button, Dialog, DialogActions, TextField } from '@material-ui/core'
import DialogContent from '@material-ui/core/DialogContent'

export const ExpressRecipeFormModalContainer = ({
  isModalOpen,
  onAddRecipeClick,
  onClose,
}) => {
  const [recipeName, setRecipeName] = useState('')

  return <ExpressRecipeFormModal
    isModalOpen={isModalOpen}
    recipeName={recipeName}
    onAddRecipeClick={() => onAddRecipeClick(recipeName)}
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
    <Dialog
      data-name='express-recipe-form-modal'
      aria-labelledby="simple-modal-title"
      aria-describedby="simple-modal-description"
      open={isModalOpen}
      onClose={onClose}
      disableAutoFocus={true}>
      <DialogContent dividers>
        <TextField
          fullWidth
          data-express-recipe-form-recipe-name
          onChange={(e) => onRecipeNameChange(e.target.value)}
          value={recipeName}
          label='Name'
          type='search'
          autoFocus
        />
      </DialogContent>
      <DialogActions>
        <Button
          data-express-recipe-form-submit-button
          onClick={onAddRecipeClick}
          variant='contained'
          href=''
          color='primary'>
          Add
        </Button>
      </DialogActions>
    </Dialog>
  )
}

ExpressRecipeFormModal.propTypes = {
  isModalOpen: PropTypes.bool,
  recipeName: PropTypes.string,
  onRecipeNameChange: PropTypes.func,
  onAddRecipeClick: PropTypes.func,
  onClose: PropTypes.func,
}