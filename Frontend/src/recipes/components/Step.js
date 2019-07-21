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
      }
    }
  )(NoteIcon)

  return (
    <Paper elevation={1} className='paper'>
      <Grid container>
        <Grid item container sm={1} alignItems='flex-start' justify='space-between' direction='column'>
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
        </Grid>
        <Grid item sm={11}>
          <Typography variant="body2" data-step>
            {step}
          </Typography>
        </Grid>
        {displayNote &&
        <Grid item sm={11}>
          <Typography variant="body2" className={`step-note-content-${number}`} data-step-note>
            {note}
          </Typography>
        </Grid>}
      </Grid>
    </Paper>
  )
}

Step.propTypes = {
  number: PropTypes.number,
  step: PropTypes.string,
  note: PropTypes.string,
}
