import React, { useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'

export const OneLineEditableFieldContainer = ({ initialValue, onSaveClick }) => {
  const [editableValue, setEditableValue] = useState(initialValue)
  const [isEditable, setEditable] = useState(false)

  const showEditableMode = () => {
    setEditableValue(initialValue)
    setEditable(true)
  }

  return <OneLineEditableField
    editable={isEditable}
    initialValue={initialValue}
    editableValue={editableValue}
    onEditableValueChange={setEditableValue}
    showEditableMode={showEditableMode}
    hideEditableMode={() => setEditable(false)}
    onSaveClick={() => onSaveClick(editableValue)} />
}

OneLineEditableFieldContainer.propTypes = {
  initialValue: PropTypes.string,
  onSaveClick: PropTypes.func,
}

const OneLineEditableField = ({
  editable,
  initialValue,
  editableValue,
  onEditableValueChange,
  showEditableMode,
  hideEditableMode,
  onSaveClick,
}) => {
  if (editable) {
    return <Grid container direction="row" justify="space-between" alignItems="center">
      <Grid item sm={9}>
        <TextField
          autoFocus
          multiline={true}
          className='notes-container__editable-notes'
          value={editableValue}
          onChange={(event) => onEditableValueChange(event.target.value)}
          onBlur={hideEditableMode} />
      </Grid>
      <Grid item sm={2}>
        <Button
          className='notes-container__update-notes-button'
          color='primary'
          href=''
          onMouseDown={onSaveClick}>Save</Button>
      </Grid>
    </Grid>
  } else if (initialValue) {
    return <Typography
      variant='body2'
      className='notes-container__notes-content'
      onClick={showEditableMode}>
      {initialValue}
    </Typography>
  } else {
    return <Typography
      variant='body2'
      className='notes-container__empty-notes'
      onClick={showEditableMode}>
      Add a note
    </Typography>
  }
}

OneLineEditableField.propTypes = {
  editable: PropTypes.bool,
  initialValue: PropTypes.string,
  editableValue: PropTypes.string,
  onEditableValueChange: PropTypes.func,
  showEditableMode: PropTypes.func,
  hideEditableMode: PropTypes.func,
}