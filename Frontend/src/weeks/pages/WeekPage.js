import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { Button, Grid, IconButton, Snackbar, SnackbarContent, Typography, withStyles } from '@material-ui/core'
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft'
import ChevronRightIcon from '@material-ui/icons/ChevronRight'
import CloseIcon from '@material-ui/icons/Close'

import { saveWeek, toggleModal } from 'src/weeks/actions/WeekPageActions'
import { createCart } from 'src/carts/actions/NewCartActions'
import { WeekPane } from 'src/weeks/components/WeekPane'
import { RecipeListModalContainer } from 'src/weeks/components/RecipeListModal'
import { setState } from 'src/RootReducer'

import './WeekPage.css'
import { PageTitle } from '../../PageTitle'

export const WEEKS_IN_A_YEAR = 52

const Title = withStyles({
  root: {
    display: 'inline',
  },
})(Typography)

const IconButtonStyled = withStyles({
  root: {
    'margin': '0 5px',
  },
})(IconButton)

export const WeekPage = ({
  week,
  history,
  openModal,
  saveWeek,
  createCart,
  showSuccessMessage,
  onSuccessButtonClose,
}) => {
  const onPreviousWeekClick = () => {
    const previousWeekNumber = week.weekNumber <= 1 ? WEEKS_IN_A_YEAR : (week.weekNumber - 1)
    const previousYear = week.weekNumber <= 1 ? (week.year - 1) : week.year
    history.push(`/weeks/${previousYear}/${previousWeekNumber}`)
  }

  const onNextWeekClick = () => {
    const nextWeekNumber = week.weekNumber >= WEEKS_IN_A_YEAR ? 1 : (week.weekNumber + 1)
    const nextYear = week.weekNumber >= WEEKS_IN_A_YEAR ? (week.year + 1) : week.year
    history.push(`/weeks/${nextYear}/${nextWeekNumber}`)
  }

  const onCreateCartClick = () => {
    const recipeIds = week.days
      .map(day => [day.lunch?.id, day.diner?.id])
      .reduce((prev, curr) => prev.concat(curr))
      .filter(recipeId => recipeId > 0)

    saveWeek(week)
    createCart(recipeIds)
  }

  const title = `Year ${week.year}, week ${week.weekNumber}`

  return (
    <Grid container spacing={3} justify='center'>
      <PageTitle title={title} />
      <Snackbar
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
        open={showSuccessMessage}
        autoHideDuration={3000}
        onClose={onSuccessButtonClose}>
        <SnackbarContent
          className={'week-page__success-message'}
          message={<span>Week saved successfully</span>}
          action={[
            <IconButton
              key="close"
              aria-label="Close"
              color="inherit"
              href=''
              onClick={onSuccessButtonClose}
              className={'week-page__success-message-close'}>
              <CloseIcon />
            </IconButton>,
          ]}
        />
      </Snackbar>
      <Grid item xs={3}>
      </Grid>
      <Grid item container xs={6} justify='center' alignItems='center'>
        <IconButtonStyled fontSize='large' className='week-page__previous-week' onClick={onPreviousWeekClick}>
          <ChevronLeftIcon />
        </IconButtonStyled>
        <Title variant='h6' className='witchcraft-title'>{title}</Title>
        <IconButtonStyled fontSize='large' className='week-page__next-week' onClick={onNextWeekClick}>
          <ChevronRightIcon />
        </IconButtonStyled>
      </Grid>
      <Grid item container xs={3} spacing={3} justify='flex-end' alignItems='center'>
        <Grid item>
          <Button
            className='week-page__save-button'
            variant='contained'
            color='primary'
            href=''
            onClick={() => saveWeek(week)}>
            Save
          </Button>
        </Grid>
        <Grid item>
          <Button
            className='week-page__create-cart-button'
            variant='contained'
            color='primary'
            href=''
            onClick={onCreateCartClick}>
            Create Cart
          </Button>
        </Grid>
      </Grid>
      <Grid item xs={12}>
        <WeekPane week={week} onMealClick={openModal} />
      </Grid>
      <RecipeListModalContainer />
    </Grid>
  )
}

WeekPage.propTypes = {
  week: PropTypes.object,
  history: PropTypes.object,
  openModal: PropTypes.func,
  saveWeek: PropTypes.func,
  createCart: PropTypes.func,
  showSuccessMessage: PropTypes.bool,
  onSuccessButtonClose: PropTypes.func,
}

const mapStateToProps = state => {
  return {
    week: state.app.week,
    showSuccessMessage: state.app.pages.weekPage.showSuccessMessage,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
    openModal: (day, meal, currentRecipeId) => toggleModal(true, day, meal, currentRecipeId),
    saveWeek: saveWeek,
    createCart: createCart,
    onSuccessButtonClose: () => setState('pages.weekPage.showSuccessMessage', false),
  }, dispatch)
}

export const WeekPageContainer = connect(mapStateToProps, mapDispatchToProps)(WeekPage)
