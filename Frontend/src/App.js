import React from 'react'
import Grid from '@material-ui/core/Grid'
import CssBaseline from '@material-ui/core/CssBaseline'

import './App.css'
import { Header } from './Header'
import { Content } from './Content'

export const App = () => {
  return (
    <Grid container justify='center'>
      <CssBaseline />
      <Grid item xs={10} className='header'>
        <Header />
      </Grid>
      <Grid item xs={10} className='content'>
        <Content />
      </Grid>
    </Grid>
  )
}
