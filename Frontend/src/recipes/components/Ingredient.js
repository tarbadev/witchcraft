import React from 'react'
import PropTypes from 'prop-types'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import Grid from '@material-ui/core/Grid'

import styles from 'src/recipes/components/Ingredient.css'

export const Ingredient = ({quantity, unit, ingredient}) => {
  const quantityAndUnit = quantity + ' ' + unit

  return (
    <Paper elevation={1} className={styles.paper}>
      <Grid container>
        <Grid item sm={10}>
          <Typography variant="body2" className={styles.ingredient}>
            {ingredient}
          </Typography>
        </Grid>
        <Grid item sm={2}>
          <Typography variant="body2" align="right" className={styles.unit}>
            {quantityAndUnit}
          </Typography>
        </Grid>
      </Grid>
    </Paper>
  )
}

Ingredient.propTypes = {
  quantity: PropTypes.number,
  unit: PropTypes.string,
  ingredient: PropTypes.string,
}
