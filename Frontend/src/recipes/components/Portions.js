import React, { useState } from 'react'
import PropTypes from 'prop-types'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import Grid from '@material-ui/core/Grid'

import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import { useAppContext } from 'src/app/components/StoreProvider'
import { updatePortions } from 'src/recipes/actions/RecipeActions'
import ClickAwayListener from '@material-ui/core/ClickAwayListener'

export const PortionsContainer = ({ portions, recipeId, onPortionsUpdated }) => {
  const { dispatch } = useAppContext()
  const [isEditable, setEditable] = useState(false)
  const [editedPortions, setEditedPortions] = useState(portions)

  return <Portions
    portions={portions}
    editable={isEditable}
    showEditableMode={() => setEditable(true)}
    hideEditableMode={() => setEditable(false)}
    editedPortions={editedPortions}
    onPortionsChange={(newName) => setEditedPortions(newName)}
    onSaveClick={() => dispatch(updatePortions(
      recipeId,
      editedPortions,
      recipe => {
        onPortionsUpdated(recipe)
        setEditable(false)
      },
    ))}
  />
}

PortionsContainer.propTypes = {
  portions: PropTypes.string,
  recipeId: PropTypes.number,
  onPortionsUpdated: PropTypes.func,
}

const Portions = ({
  portions,
  editable,
  showEditableMode,
  hideEditableMode,
  editedPortions,
  onPortionsChange,
  onSaveClick,
}) => {
  let portionsBody

  if (editable) {
    portionsBody = (
      <ClickAwayListener onClickAway={hideEditableMode}>
        <Grid container data-portions-container spacing={1} alignItems='center'>
          <Grid item>
            <TextField
              value={editedPortions}
              data-edit-portions
              label='Portions'
              style={{ width: '4em' }}
              onChange={({ target }) => onPortionsChange(target.value)}
            />
          </Grid>
          <Grid item>
            <Button
              color='primary'
              href=''
              data-save-portions
              onMouseDown={onSaveClick}
            >
              Save
            </Button>
          </Grid>
        </Grid>
      </ClickAwayListener>
    )
  } else {
    portionsBody = (
      <Typography variant='h6' className='witchcraft-title' data-portions-container onClick={showEditableMode}>
        Portions: <span data-portions-value='true'>{portions}</span>
      </Typography>
    )
  }

  return (
    <Paper elevation={1} style={{ padding: '.5em 1em', cursor: 'pointer' }}>
      {portionsBody}
    </Paper>
  )
}

Portions.propTypes = {
  portions: PropTypes.string,
  editable: PropTypes.bool,
  showEditableMode: PropTypes.func,
  editedPortions: PropTypes.string,
  onPortionsChange: PropTypes.func,
  hideEditableMode: PropTypes.func,
  onSaveClick: PropTypes.func,
}
