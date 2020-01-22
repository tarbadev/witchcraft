import React from 'react'
import { Container, makeStyles } from '@material-ui/core'

import './App.css'
import DefaultRecipePicture from 'src/recipes/images/recipe-default.png'
import { Content } from './Content'
import { Header } from 'src/app/components/Header'

export const onRecipeImageNotFoundError = (e) => e.target.src = DefaultRecipePicture

const useStyles = makeStyles(theme => ({
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
}))

export const App = () => {
  const classes = useStyles()

  return (
    <div>
      <Header />
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        <Container className={classes.container}>
          <Content />
        </Container>
      </main>
    </div>
  )
}