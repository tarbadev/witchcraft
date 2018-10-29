import React from 'react'
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import Grid from '@material-ui/core/Grid'
import Paper from '@material-ui/core/Paper'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'
import { withStyles } from '@material-ui/core/styles'
import { setState } from 'app-root/RootReducer'

import { urlInputChange, submitUrlForm } from 'app-actions/newRecipeActions'

const styles = theme => ({
  root: {
    flexGrow: 1,
  },
  paper: {
     padding: '.5em 1em !important'
  }
})

export const NewRecipe = ({ classes, changeUrlFormInput, addRecipeFromUrl, url, redirect, history, setState }) => {
  const onUrlInputChange = (event) => {
    changeUrlFormInput(event.target.value)
  }

  const onUrlFormSubmit = (e) => {
    addRecipeFromUrl({ url: url })
  }

  if (redirect) {
    history.push('/recipes')
    setState("newRecipe.form", { recipeAdded: false, url: '' })
  }

  return (
    <Grid container spacing={24}>
      <Grid item xs={12}>
        <Typography variant="display1">New Recipe</Typography>
      </Grid>
      <Grid item xs={6}>
        <Paper className={classes.paper}>
          <Typography variant="title">Add manually</Typography>
        </Paper>
      </Grid>
      <Grid item xs={6}>
        <Paper className={classes.paper}>
          <Grid container spacing={24}>
            <Grid item xs={12} >
              <Typography variant="title">Add from URL</Typography>
            </Grid>
            <Grid item xs={12} >
              <TextField fullWidth onChange={onUrlInputChange} value={url} label="http://example.com/recipe/32434" type="search"/>
            </Grid>
            <Grid item xs={12} >
              <Button variant="contained" color="primary" onClick={onUrlFormSubmit}>
                Submit
              </Button>
            </Grid>
          </Grid>
        </Paper>
      </Grid>
    </Grid>
  )
}

const mapStateToProps = state => {
	return {
    url: state.newRecipe.form.url,
    redirect: state.newRecipe.form.recipeAdded
  }
}

const mapDispatchToProps = (dispatch) => {
	return bindActionCreators({
    changeUrlFormInput: urlInputChange,
    addRecipeFromUrl: submitUrlForm,
    setState: setState
  },
		dispatch
	)
}

const NewRecipeWithStyles = withStyles(styles)(NewRecipe)

export const NewRecipeContainer = connect(
	mapStateToProps,
	mapDispatchToProps
)(NewRecipeWithStyles)
