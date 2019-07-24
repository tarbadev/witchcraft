import React from 'react'
import Grid from '@material-ui/core/Grid'
import CssBaseline from '@material-ui/core/CssBaseline'

import './App.css'
import { HeaderContainer } from './Header'
import { Content } from './Content'

import DefaultRecipePicture from 'src/recipes/images/recipe-default.png'
export const onRecipeImageNotFoundError = (e) => e.target.src = DefaultRecipePicture

export const App = () =>
  (
    <Grid container justify='center'>
      <CssBaseline />
      <Grid item xs={10} className='header'>
        <HeaderContainer />
      </Grid>
      <Grid item xs={10} className='content'>
        <Content />
      </Grid>
    </Grid>
  )
