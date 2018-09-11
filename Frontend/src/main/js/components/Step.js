import React, { Component } from 'react';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

import styles from 'app-components/Step.css';

export default class Step extends Component {
  render() {
    return (
      <Paper elevation={1} className={styles.paper}>
        <Grid container>
          <Grid item sm={1}>
            <Typography variant="body2" align="center" className={styles.numberedList}>
              {this.props.number}
            </Typography>
          </Grid>
          <Grid item sm={11}>
            <Typography variant="body2">
              {this.props.step}
            </Typography>
          </Grid>
        </Grid>
      </Paper>
    )
  }
}
