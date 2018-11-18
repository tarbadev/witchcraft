import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { withStyles } from '@material-ui/core/styles'
import Button from '@material-ui/core/Button'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import Table from '@material-ui/core/Table'
import TableHead from '@material-ui/core/TableHead'
import TableRow from '@material-ui/core/TableRow'
import TableCell from '@material-ui/core/TableCell'
import TableBody from '@material-ui/core/TableBody'
import IconButton from '@material-ui/core/IconButton'
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft'
import ChevronRightIcon from '@material-ui/icons/ChevronRight'

import { toggleModal, saveWeek } from 'app-actions/WeekPageActions'
import { WeekRecipeCell } from 'app-components/WeekRecipeCell'
import { RecipeListModalContainer } from 'app-components/RecipeListModal'

export const WEEKS_IN_A_YEAR = 52

const BodyRow = withStyles({
  root: {
    height: 200,
  },
})(TableRow)

const Title = withStyles({
  root: {
    display: 'inline',
  },
})(Typography)

const DayCell = withStyles({
  root: {
    width: 'calc(100% / 7)',
    padding: '4px 18px',
  },
})(TableCell)

const FirstColumnCell = withStyles({
  root: {
    width: '50px',
    padding: '15px',
  },
})(TableCell)

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

  const header = week.days.map((day, index) => (
    <DayCell key={`header-${index}`}>
      <Typography variant='button'>{day.name}</Typography>
    </DayCell>
  ))

  const lunches = week.days.map((day, index) => {
    return (
      <WeekRecipeCell
        key={`lunch-${index}`}
        recipe={day.lunch}
        onCellClick={() => openModal(day.name.toLowerCase(), 'lunch', day.lunch?.id)} />
    )
  })

  const diners = week.days.map((day, index) => {
    return (
      <WeekRecipeCell
        key={`diner-${index}`}
        recipe={day.diner}
        onCellClick={() => openModal(day.name.toLowerCase(), 'diner', day.diner?.id)} />
    )
  })

  return (
    <Grid container spacing={24} justify='center'>
      <Grid item xs={1}>
      </Grid>
      <Grid item container xs={10} justify='center' alignItems='center'>
        <IconButtonStyled fontSize='large' className='week-page__previous-week' onClick={onPreviousWeekClick}>
          <ChevronLeftIcon />
        </IconButtonStyled>
        <Title variant='title'>Year {week.year}, week {week.weekNumber}</Title>
        <IconButtonStyled fontSize='large' className='week-page__next-week' onClick={onNextWeekClick}>
          <ChevronRightIcon />
        </IconButtonStyled>
      </Grid>
      <Grid item xs={1}>
        <Button
          className='week-page__save-button'
          variant='contained'
          color='primary'
          onClick={() => onSaveClick(week)} >
          Save
        </Button>
      </Grid>
      <Grid item xs={12}>
        <Paper>
          <Table>
            <TableHead>
              <TableRow>
                <FirstColumnCell></FirstColumnCell>
                {header}
              </TableRow>
            </TableHead>
            <TableBody>
              <BodyRow>
                <FirstColumnCell>
                  <Typography variant='button'>Lunch</Typography>
                </FirstColumnCell>
                {lunches}
              </BodyRow>
              <BodyRow>
                <FirstColumnCell>
                  <Typography variant='button'>Diner</Typography>
                </FirstColumnCell>
                {diners}
              </BodyRow>
            </TableBody>
          </Table>
        </Paper>
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
  }, dispatch)
}

export const WeekPageContainer = connect(mapStateToProps, mapDispatchToProps)(WeekPage)
