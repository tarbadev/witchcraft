import React from 'react'
import PropTypes from 'prop-types'

import { ListItemText } from '@material-ui/core'
import ListItem from '@material-ui/core/ListItem'
import { OneLineEditableFieldContainer } from 'src/app/components/OneLineEditableField'

export const Step = ({ step, note, onSaveNote, lastItem }) => {

  return (
    <ListItem disableGutters divider={!lastItem} alignItems='flex-start'>
      <ListItemText
        primary={<span data-step='true'>{step}</span>}
        secondary={
          <span data-step-note={true}>
            <OneLineEditableFieldContainer
              initialValue={note}
              prefix='Note:'
              onSaveClick={newNotes => onSaveNote(newNotes)} />
          </span>
        }
      />
    </ListItem>
  )
}

Step.propTypes = {
  step: PropTypes.string,
  note: PropTypes.string,
  onSaveNote: PropTypes.func,
  lastItem: PropTypes.bool,
}
