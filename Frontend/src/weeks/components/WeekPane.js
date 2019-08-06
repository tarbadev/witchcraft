import React from 'react'
import PropTypes from 'prop-types'
import { withStyles } from '@material-ui/core/styles'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import Table from '@material-ui/core/Table'
import TableHead from '@material-ui/core/TableHead'
import TableRow from '@material-ui/core/TableRow'
import TableCell from '@material-ui/core/TableCell'
import TableBody from '@material-ui/core/TableBody'

import { WeekRecipeCell } from './WeekRecipeCell'

export const WeekPane = ({
  week,
  onMealClick,
}) => {

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

  const BodyRow = withStyles({
    root: {
      height: 200,
    },
  })(TableRow)

  const header = week.days.map((day, index) => (
    <DayCell key={`header-${index}`} align='center'>
      <Typography variant='button'>{day.name}</Typography>
    </DayCell>
  ))

  const lunches = week.days.map((day, index) => {
    let onCellClick
    if (onMealClick) {
      onCellClick = () => onMealClick(day.name.toLowerCase(), 'lunch', day.lunch?.id)
    }

    return (
      <WeekRecipeCell
        key={`lunch-${index}`}
        recipe={day.lunch}
        onCellClick={onCellClick}
        dataTag={`lunch-${day.name}`}
      />
    )
  })

  const diners = week.days.map((day, index) => {
    let onCellClick
    if (onMealClick) {
      onCellClick = () => onMealClick(day.name.toLowerCase(), 'diner', day.diner?.id)
    }

    return (
      <WeekRecipeCell
        key={`diner-${index}`}
        recipe={day.diner}
        onCellClick={onCellClick}
        dataTag={`diner-${day.name}`}
      />
    )
  })

  return (
    <Paper>
      <Table>
        <TableHead>
          <TableRow>
            <FirstColumnCell></FirstColumnCell>
            {header}
          </TableRow>
        </TableHead>
        <TableBody>
          <BodyRow data-lunch>
            <FirstColumnCell>
              <Typography variant='button'>Lunch</Typography>
            </FirstColumnCell>
            {lunches}
          </BodyRow>
          <BodyRow data-diner>
            <FirstColumnCell>
              <Typography variant='button'>Diner</Typography>
            </FirstColumnCell>
            {diners}
          </BodyRow>
        </TableBody>
      </Table>
    </Paper>
  )
}

WeekPane.propTypes = {
  week: PropTypes.object,
  onMealClick: PropTypes.func,
}
