import React from 'react'
import PropTypes from 'prop-types'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import Grid from '@material-ui/core/Grid'

import styles from 'app-components/Step.css'

export const Step = ({number, step}) => {
  return (
    <Paper elevation={1} className={styles.paper}>
      <Grid container>
        <Grid item sm={1}>
          <Typography variant="body2" align="center" className={styles.numberedList}>
            {number}
          </Typography>
        </Grid>
        <Grid item sm={11}>
          <Typography variant="body2">
            {step}
          </Typography>
        </Grid>
      </Grid>
    </Paper>
  )
}

Step.propTypes = {
  number: PropTypes.number,
  step: PropTypes.string,
}
