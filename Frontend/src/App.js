import React from 'react'
import Grid from '@material-ui/core/Grid'
import CssBaseline from '@material-ui/core/CssBaseline'

import styles from './App.css'
import { Header } from './Header'
import { Content } from './Content'

export const App = () => {
  return (
    <Grid container spacing={24} justify='center'>
      <CssBaseline />
      <Grid item xs={10} className={styles.header}>
        <Header />
      </Grid>
      <Grid item xs={10}>
        <Content />
      </Grid>
    </Grid>
  )
}
