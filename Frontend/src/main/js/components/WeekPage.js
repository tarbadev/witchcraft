import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { withStyles } from '@material-ui/core/styles'
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

export const WeekPage = ({
  week,
  history,
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
    <TableCell key={`header-${index}`}>
      <Typography>{day.name}</Typography>
    </TableCell>
  ))

  const lunches = week.days.map((day, index) => {
    return (
      <TableCell key={`lunch-${index}`}>
        <Typography>{day.lunch?.name}</Typography>
      </TableCell>
    )
  })

  const diners = week.days.map((day, index) => {
    return (
      <TableCell key={`diner-${index}`}>
        <Typography>{day.diner?.name}</Typography>
      </TableCell>
    )
  })

  return (
    <Grid container spacing={24}>
      <Grid item xs={12}>
        <IconButton fontSize='large' className='week-page__previous-week' onClick={onPreviousWeekClick}>
          <ChevronLeftIcon />
        </IconButton>
        <Title variant='title'>Year {week.year}, week {week.weekNumber}</Title>
        <IconButton fontSize='large' className='week-page__next-week' onClick={onNextWeekClick}>
          <ChevronRightIcon />
        </IconButton>
      </Grid>
      <Grid item xs={12}>
        <Paper>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell></TableCell>
                {header}
              </TableRow>
            </TableHead>
            <TableBody>
              <BodyRow>
                <TableCell>
                  <Typography>Lunch</Typography>
                </TableCell>
                {lunches}
              </BodyRow>
              <BodyRow>
                <TableCell>
                  <Typography>Diner</Typography>
                </TableCell>
                {diners}
              </BodyRow>
            </TableBody>
          </Table>
        </Paper>
      </Grid>
    </Grid>
  )
}

WeekPage.propTypes = {
  week: PropTypes.object,
  history: PropTypes.object,
}

const mapStateToProps = state => {
  return {
    week: state.week,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
  }, dispatch)
}

export const WeekPageContainer = connect(mapStateToProps, mapDispatchToProps)(WeekPage)
