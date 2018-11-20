import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { withStyles } from '@material-ui/core/styles'
import Button from '@material-ui/core/Button'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'
import IconButton from '@material-ui/core/IconButton'
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft'
import ChevronRightIcon from '@material-ui/icons/ChevronRight'

import { toggleModal, saveWeek } from 'app-actions/WeekPageActions'
import { WeekPane } from 'app-components/WeekPane'
import { RecipeListModalContainer } from 'app-components/RecipeListModal'
import { createCart } from 'app-actions/NewCartActions'

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
  onSaveClick,
  createCart,
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
      .map(day => [{ id: day.lunch?.id }, { id: day.diner?.id }])
      .reduce((prev, curr) => prev.concat(curr))
      .filter(recipeId => recipeId.id > 0)

    createCart(recipeIds)
  }

  return (
    <Grid container spacing={24} justify='center'>
      <Grid item xs={3}>
      </Grid>
      <Grid item container xs={6} justify='center' alignItems='center'>
        <IconButtonStyled fontSize='large' className='week-page__previous-week' onClick={onPreviousWeekClick}>
          <ChevronLeftIcon />
        </IconButtonStyled>
        <Title variant='title'>Year {week.year}, week {week.weekNumber}</Title>
        <IconButtonStyled fontSize='large' className='week-page__next-week' onClick={onNextWeekClick}>
          <ChevronRightIcon />
        </IconButtonStyled>
      </Grid>
      <Grid item container xs={3} spacing={24} justify='flex-end' alignItems='center'>
        <Grid item>
          <Button
            className='week-page__save-button'
            variant='contained'
            color='primary'
            onClick={() => onSaveClick(week)} >
            Save
          </Button>
        </Grid>
        <Grid item>
          <Button
            className='week-page__create-cart-button'
            variant='contained'
            color='primary'
            onClick={onCreateCartClick} >
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
  onSaveClick: PropTypes.func,
  createCart: PropTypes.func,
}

const mapStateToProps = state => {
  return {
    week: state.week,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
    openModal: (day, meal, currentRecipeId) => toggleModal(true, day, meal, currentRecipeId),
    onSaveClick: saveWeek,
    createCart: createCart,
  }, dispatch)
}

export const WeekPageContainer = connect(mapStateToProps, mapDispatchToProps)(WeekPage)
