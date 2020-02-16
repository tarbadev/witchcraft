import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Container, Dialog, makeStyles } from '@material-ui/core'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import Grid from '@material-ui/core/Grid'
import Paper from '@material-ui/core/Paper'
import Slide from '@material-ui/core/Slide'
import CloseIcon from '@material-ui/icons/Close'
import { IngredientContainer } from 'src/recipes/components/IngredientContainer'
import IconButton from '@material-ui/core/IconButton'
import Button from '@material-ui/core/Button'

export const StartCooking = ({ open, recipe, onClose }) => {
  const [currentIndex, setCurrentIndex] = useState(0)
  const [currentStep, setCurrentStep] = useState('')
  const [currentStepNote, setCurrentStepNote] = useState('')

  useEffect(() => setCurrentStep(recipe.steps[currentIndex] ? recipe.steps[currentIndex].name : ''),
    [recipe, currentIndex])
  useEffect(() => setCurrentStepNote(recipe.steps[currentIndex] ? `Note: ${recipe.steps[currentIndex].note}` : ''),
    [recipe, currentIndex])

  return <StartCookingDisplay
    open={open}
    title={recipe.name}
    step={currentStep}
    note={currentStepNote}
    ingredients={recipe.ingredients}
    closeDialog={onClose}
    nextStep={currentIndex !== (recipe.steps.length - 1) ? () => setCurrentIndex(currentIndex + 1) : null}
  />
}

StartCooking.propTypes = {
  open: PropTypes.bool.isRequired,
  recipe: PropTypes.object.isRequired,
  onClose: PropTypes.func.isRequired,
}

const useStyles = makeStyles(theme => ({
  appBar: {
    position: 'relative',
  },
  title: {
    textTransform: 'capitalize',
    flex: 1,
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(1),
  },
}))

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />
})

const StartCookingDisplay = ({ open, title, step, note, ingredients, closeDialog, nextStep }) => {
  const classes = useStyles()

  const button = nextStep
    ? <Button fullWidth color='primary' variant='contained' onClick={nextStep} data-start-cooking-next-button>
      Next
    </Button>
    : <Button fullWidth color='primary' variant='contained' onClick={closeDialog} data-start-cooking-finish-button>
      Finish
    </Button>

  return <Dialog fullScreen open={open} TransitionComponent={Transition}>
    <AppBar className={classes.appBar}>
      <Toolbar>
        <Typography data-start-cooking-title variant="h6" className={classes.title}>
          {title}
        </Typography>
        <IconButton edge='end' color='inherit' onClick={closeDialog} data-start-cooking-close-button>
          <CloseIcon />
        </IconButton>
      </Toolbar>
    </AppBar>
    <Container maxWidth='xl'>
      <Grid container spacing={1} className={classes.container}>
        <Grid item xs={12}>
          <Paper className={classes.paper} data-start-cooking-ingredients>
            <Typography variant='h6' className='witchcraft-title'>Ingredients</Typography>
            <IngredientContainer ingredients={ingredients} editable={false} />
          </Paper>
        </Grid>
        <Grid item xs={12}>
          <Paper className={classes.paper}>
            <Typography variant='h6' className='witchcraft-title'>Step</Typography>
            <Typography data-start-cooking-step>{step}</Typography>
            <Typography variant='caption' data-start-cooking-step-note>{note}</Typography>
          </Paper>
        </Grid>
        <Grid item xs={12}>
          {button}
        </Grid>
      </Grid>
    </Container>
  </Dialog>
}

StartCookingDisplay.propTypes = {
  open: PropTypes.bool,
  title: PropTypes.string,
  step: PropTypes.string,
  note: PropTypes.string,
  ingredients: PropTypes.array,
  closeDialog: PropTypes.func,
  nextStep: PropTypes.func,
}