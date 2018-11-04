import React from 'react'
import Grid from '@material-ui/core/Grid'

import styles from './App.css'
import {Header} from './Header'
import {Content} from './Content'

export const App = () => {
  return (
    <Grid container spacing={24} justify='center'>
      <Grid item xs={10} className={styles.header}>
        <Header/>
      </Grid>
      <Grid item xs={10}>
        <Content/>
      </Grid>
    </Grid>
  )
}
