import React, { useEffect, useState } from 'react'
import Grid from '@material-ui/core/Grid'
import { PageTitle } from 'src/app/components/PageTitle'
import moment from 'moment'
import PropTypes from 'prop-types'
import { useAppContext } from 'src/app/components/StoreProvider'
import { initialState } from 'src/app/RootReducer'
import { WEEK } from 'src/app/components/Header'
import { getWeek } from 'src/weeks/actions/WeekActions'
import Typography from '@material-ui/core/Typography'
import { saveWeek, setRecipeToWeek, toggleModal } from 'src/weeks/actions/WeekPageActions'
import { RecipeListModalContainer } from 'src/weeks/components/RecipeListModal'
import { IconButton, Snackbar, SnackbarContent, useMediaQuery, useTheme } from '@material-ui/core'
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft'
import ChevronRightIcon from '@material-ui/icons/ChevronRight'
import CloseIcon from '@material-ui/icons/Close'
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart'
import MenuItem from '@material-ui/core/MenuItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'
import { createCart } from 'src/carts/actions/NewCartActions'
import { MultipleDayPane } from 'src/weeks/components/MultipleDayPane'

export const WEEKS_IN_A_YEAR = 52

export const WeekPage = ({ history, weekNumber, year }) => {
  const theme = useTheme()
  const isMobile = useMediaQuery(theme.breakpoints.down('xs'))
  const isTablet = useMediaQuery(theme.breakpoints.down('sm'))

  const { state, dispatch, setHeaderConfig } = useAppContext()
  const [modal, setModal] = useState(state.pages.weekPage.modal)
  const [week, setWeek] = useState(state.week)
  const [showSuccessMessage, setShowSuccessMessage] = useState(false)
  const [dayIndex, setDayIndex] = useState(0)

  const dayOfWeek = moment(`${year}-${weekNumber}-${dayIndex + 1}`, 'GGGG-W-E')
  let title = ''
  if (isMobile) {
    title = dayOfWeek.format('MMMM Do YYYY')
  } else if (isTablet) {
    const inThreeDays = dayOfWeek.clone().add(3, 'days')
    title = `${dayOfWeek.format('MMMM Do YYYY')} to ${inThreeDays.format('MMMM Do YYYY')}`
  } else {
    const lastDayOfWeek = dayOfWeek.clone().add(6, 'days')
    title = `${dayOfWeek.format('MMMM Do YYYY')} to ${lastDayOfWeek.format('MMMM Do YYYY')}`
  }

  useEffect(() => setHeaderConfig({
    ...initialState.headerConfig,
    currentLink: WEEK,
    title: title,
    menuList: [
      <MenuItem
        key='menu-create-cart'
        data-create-cart-button
        onClick={() => {
          const recipeIds = week.days
            .map(day => day.lunch.map(recipe => recipe.id).concat(day.diner.map(recipe => recipe.id)))
            .reduce((prev, curr) => prev.concat(curr))

          dispatch(createCart(recipeIds, cart => history.push(`/carts/${cart.id}`)))
        }}
      >
        <ListItemIcon>
          <ShoppingCartIcon />
        </ListItemIcon>
        <ListItemText primary="Create cart" />
      </MenuItem>,
    ],
  }), [week, dayIndex, isMobile, isTablet])
  useEffect(() => dispatch(getWeek(year, weekNumber, data => setWeek(data))), [year, weekNumber])
  useEffect(() => {
    let defaultDayIndex = 0
    if (history.location.search.includes('previous=true')) {
      if (isTablet) {
        defaultDayIndex = 4
      } else {
        defaultDayIndex = 6
      }
    }
    return setDayIndex(defaultDayIndex)
  }, [isMobile, isTablet])

  const onSaveWeekSuccess = savedWeek => {
    setWeek(savedWeek)
    setShowSuccessMessage(true)
  }
  const closeModal = () => setModal(toggleModal(false))
  const setRecipeAndCloseModal = (recipe, day, meal) => {
    dispatch(saveWeek(setRecipeToWeek(week, recipe, day, meal), onSaveWeekSuccess))
    closeModal()
  }

  const onPreviousClick = () => {
    if ((isMobile || isTablet) && dayIndex > 0) {
      setDayIndex(dayIndex - 1)
    } else {
      const previousWeekNumber = week.weekNumber <= 1 ? WEEKS_IN_A_YEAR : (week.weekNumber - 1)
      const previousYear = week.weekNumber <= 1 ? (week.year - 1) : week.year
      const suffix = isMobile || isTablet ? '?previous=true' : ''
      history.push(`/weeks/${previousYear}/${previousWeekNumber}${suffix}`)
    }
  }

  const onNextClick = () => {
    let maxDayIndex = isTablet ? week.days.length - 3 : week.days.length - 1

    if ((isMobile || isTablet) && dayIndex < maxDayIndex) {
      setDayIndex(dayIndex + 1)
    } else {
      const nextWeekNumber = week.weekNumber >= WEEKS_IN_A_YEAR ? 1 : (week.weekNumber + 1)
      const nextYear = week.weekNumber >= WEEKS_IN_A_YEAR ? (week.year + 1) : week.year
      history.push(`/weeks/${nextYear}/${nextWeekNumber}`)
    }
  }

  let days = week.days

  if (week.days.length > 0) {
    if (isMobile) {
      days = [week.days[dayIndex]]
    } else if (isTablet) {
      days = [week.days[dayIndex], week.days[dayIndex + 1], week.days[dayIndex + 2]]
    }
  }

  return <WeekPageDisplay
    modal={modal}
    openModal={(day, meal, currentRecipeIds) => setModal(toggleModal(true, day, meal, currentRecipeIds))}
    closeModal={closeModal}
    setRecipe={setRecipeAndCloseModal}
    showSuccessMessage={showSuccessMessage}
    onSuccessButtonClose={() => setShowSuccessMessage(false)}
    onPreviousClick={onPreviousClick}
    onNextClick={onNextClick}
    days={days}
    title={title}
  />
}

WeekPage.propTypes = {
  history: PropTypes.object,
  year: PropTypes.string,
  weekNumber: PropTypes.string,
}

const WeekPageDisplay = ({
  modal,
  openModal,
  closeModal,
  setRecipe,
  showSuccessMessage,
  onSuccessButtonClose,
  onPreviousClick,
  onNextClick,
  days,
  title,
}) => {
  return <Grid container spacing={1}>
    <PageTitle title={title} />
    <Snackbar
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      open={showSuccessMessage}
      autoHideDuration={2000}
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
    <Grid item xs={12}>
      <Typography variant='h6' className='witchcraft-title' align='center'>
        <IconButton
          fontSize='large'
          className='week-page__previous-week'
          onClick={onPreviousClick}
        >
          <ChevronLeftIcon />
        </IconButton>
        {title}
        <IconButton
          fontSize='large'
          className='week-page__next-week'
          onClick={onNextClick}
        >
          <ChevronRightIcon />
        </IconButton>
      </Typography>
    </Grid>
    <Grid item xs={12}>
      <MultipleDayPane days={days} onMealClick={openModal} />
    </Grid>
    <RecipeListModalContainer config={modal} closeModal={closeModal} setRecipe={setRecipe} />
  </Grid>
}

WeekPageDisplay.propTypes = {
  modal: PropTypes.object,
  openModal: PropTypes.func,
  closeModal: PropTypes.func,
  setRecipe: PropTypes.func,
  showSuccessMessage: PropTypes.bool,
  onSuccessButtonClose: PropTypes.func,
  onPreviousClick: PropTypes.func,
  onNextClick: PropTypes.func,
  days: PropTypes.array,
  title: PropTypes.string,
}