import React, { useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'

export const OneLineEditableFieldContainer = ({ initialValue, onSaveClick, prefix }) => {
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
    onSaveClick={() => onSaveClick(editableValue)}
    prefix={prefix}
  />
}

OneLineEditableFieldContainer.propTypes = {
  initialValue: PropTypes.string,
  onSaveClick: PropTypes.func,
  prefix: PropTypes.string,
}

const OneLineEditableField = ({
  editable,
  initialValue,
  editableValue,
  onEditableValueChange,
  showEditableMode,
  hideEditableMode,
  onSaveClick,
  prefix,
}) => {
  const DisplayTypography = ({ children }) => <Typography
    variant='body2'
    data-display-value
    component='span'
    style={{ cursor: 'pointer' }}
    onClick={showEditableMode}>
    {children}
  </Typography>

  DisplayTypography.propTypes = { children: PropTypes.node }

  if (editable) {
    return <Grid container component='span' direction="row" justify="space-between" alignItems="flex-end">
      <Grid item xs={10} component='span'>
        <TextField
          autoFocus
          multiline
          value={editableValue}
          data-edit-value
          fullWidth
          label='Note'
          component='span'
          InputProps={{ inputComponent: 'span' }}
          onChange={({ target }) => onEditableValueChange(target.value)}
          onBlur={hideEditableMode}
        />
      </Grid>
      <Grid item xs={2} component='span'>
        <Button
          className='notes-container__update-value-button'
          color='primary'
          href=''
          onMouseDown={onSaveClick}>Save</Button>
      </Grid>
    </Grid>
  } else if (initialValue) {
    return <DisplayTypography>
      {prefix ? `${prefix} ${initialValue}` : initialValue}
    </DisplayTypography>
  } else {
    return <DisplayTypography>
      Add a note
    </DisplayTypography>
  }
}

OneLineEditableField.propTypes = {
  editable: PropTypes.bool,
  initialValue: PropTypes.string,
  editableValue: PropTypes.string,
  onEditableValueChange: PropTypes.func,
  showEditableMode: PropTypes.func,
  hideEditableMode: PropTypes.func,
  prefix: PropTypes.string,
}