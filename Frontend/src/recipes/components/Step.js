import React, { useState } from 'react'
import PropTypes from 'prop-types'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import Grid from '@material-ui/core/Grid'
import ModeCommentIcon from '@material-ui/icons/ModeComment'
import AddCommentIcon from '@material-ui/icons/AddComment'

import 'src/recipes/components/Step.css'
import { withStyles } from '@material-ui/core'

export const Step = ({ number, step, note }) => {
  const [displayNote, setDisplayNote] = useState(false)
  const NoteIcon = note ? ModeCommentIcon : AddCommentIcon

  const StyledNoteIcon = withStyles(
    {
      root: {
        cursor: 'pointer',
      },
    },
  )(NoteIcon)

  const StepGrid = withStyles(
    {
      root: {
        padding: '.5em',
      },
    },
  )(Grid)

  return (
    <Paper elevation={1}>
      <Grid container>
        <StepGrid item container sm={1} alignItems='flex-start' justify='space-between' direction='column'>
          <Grid item>
            <Typography variant="body2" className={'numberedList'}>
              {number}
            </Typography>
          </Grid>
          <Grid item>
            <StyledNoteIcon
              color='action'
              className={`step-note-${number}`}
              data-step-note-icon
              onClick={() => setDisplayNote(!displayNote)} />
          </Grid>
        </StepGrid>
        <StepGrid item sm={11}>
          <Typography variant="body2" data-step>
            {step}
          </Typography>
        </StepGrid>
        {displayNote &&
        <StepGrid item sm={12} container justify='flex-start' className='notes-container-color'>
          <Typography variant="body2" className={`step-note-content-${number} notes-container__notes-content`} data-step-note>
            {note}
          </Typography>
        </StepGrid>}
      </Grid>
    </Paper>
  )
}

Step.propTypes = {
  number: PropTypes.number,
  step: PropTypes.string,
  note: PropTypes.string,
}
