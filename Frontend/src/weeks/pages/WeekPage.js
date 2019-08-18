import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Button, Grid, IconButton, Snackbar, SnackbarContent, Typography, withStyles } from '@material-ui/core'
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft'
import ChevronRightIcon from '@material-ui/icons/ChevronRight'
import CloseIcon from '@material-ui/icons/Close'

import { saveWeek, toggleModal, setRecipeToWeek } from 'src/weeks/actions/WeekPageActions'
import { createCart } from 'src/carts/actions/NewCartActions'
import { WeekPane } from 'src/weeks/components/WeekPane'

import './WeekPage.css'
import { PageTitle } from 'src/app/components/PageTitle'
import { useAppContext } from 'src/app/components/StoreProvider'
import { getWeek } from '../actions/WeekActions'
import { RecipeListModalContainer } from '../components/RecipeListModal'
import { WEEKS } from 'src/app/components/Header'

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

export const WeekPageContainer = ({ history, weekNumber, year }) => {
  const { state, dispatch, setCurrentHeader } = useAppContext()
  setCurrentHeader(WEEKS)

  const [week, setWeek] = useState(state.week)
  const [modal, setModal] = useState(state.pages.weekPage.modal)
  const [showSuccessMessage, setShowSuccessMessage] = useState(state.pages.weekPage.showSuccessMessage)

  useEffect(() => dispatch(getWeek(year, weekNumber, data => setWeek(data))), [])

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

  const onSaveWeekSuccess = savedWeek => {
    setWeek(savedWeek)
    setShowSuccessMessage(true)
  }

  const closeModal = () => setModal(toggleModal(false))
  const setRecipeAndCloseModal = (recipe, day, meal) => {
    setWeek(setRecipeToWeek(week, recipe, day, meal))
    closeModal()
  }
  return <WeekPage
    week={week}
    saveWeek={() => dispatch(saveWeek(week, onSaveWeekSuccess))}
    createCart={recipeIds => dispatch(createCart(recipeIds, cart => history.push(`/carts/${cart.id}`)))}
    onPreviousWeekClick={onPreviousWeekClick}
    onNextWeekClick={onNextWeekClick}
    showSuccessMessage={showSuccessMessage}
    onSuccessButtonClose={() => setShowSuccessMessage(false)}
    modal={modal}
    openModal={(day, meal, currentRecipeId) => setModal(toggleModal(true, day, meal, currentRecipeId))}
    closeModal={closeModal}
    setRecipe={setRecipeAndCloseModal}
  />
}

WeekPageContainer.propTypes = {
  history: PropTypes.object,
  match: PropTypes.object,
  year: PropTypes.string,
  weekNumber: PropTypes.string,
}

export const WeekPage = ({
  week,
  saveWeek,
  createCart,
  showSuccessMessage,
  onSuccessButtonClose,
  onPreviousWeekClick,
  onNextWeekClick,
  modal,
  openModal,
  closeModal,
  setRecipe,
}) => {
  const onCreateCartClick = () => {
    const recipeIds = week.days
      .map(day => [day.lunch?.id, day.diner?.id])
      .reduce((prev, curr) => prev.concat(curr))
      .filter(recipeId => recipeId > 0)

    saveWeek()
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
              className='week-page__success-message-close'>
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
            onClick={saveWeek}>
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
      <RecipeListModalContainer config={modal} closeModal={closeModal} setRecipe={setRecipe} />
    </Grid>
  )
}

WeekPage.propTypes = {
  week: PropTypes.object,
  saveWeek: PropTypes.func,
  createCart: PropTypes.func,
  showSuccessMessage: PropTypes.bool,
  modal: PropTypes.object,
  openModal: PropTypes.func,
  closeModal: PropTypes.func,
  onSuccessButtonClose: PropTypes.func,
  onPreviousWeekClick: PropTypes.func,
  onNextWeekClick: PropTypes.func,
  setRecipe: PropTypes.func,
}