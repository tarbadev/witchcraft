import React, { Component } from 'react';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

import styles from 'app-components/Ingredient.css';

export default class Ingredient extends Component {
  render() {
    let quantityAndUnit = this.props.quantity + ' ' + this.props.unit;

    return (
      <Paper elevation={1} className={styles.paper}>
        <Grid container>
          <Grid item sm={10}>
            <Typography variant="body2" className={styles.ingredient}>
              {this.props.ingredient}
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
}
