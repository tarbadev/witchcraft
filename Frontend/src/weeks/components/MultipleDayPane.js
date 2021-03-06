import React from 'react'
import PropTypes from 'prop-types'
import { withStyles } from '@material-ui/core/styles'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import TableRow from '@material-ui/core/TableRow'
import TableCell from '@material-ui/core/TableCell'

import { WeekRecipeCell } from './WeekRecipeCell'
import TableHead from '@material-ui/core/TableHead'
import Table from '@material-ui/core/Table'
import TableBody from '@material-ui/core/TableBody'

export const MultipleDayPane = ({ days, onMealClick }) => {
  const DayCell = withStyles({
    root: {
      padding: '4px 0px',
    },
  })(TableCell)

  const FirstColumnCell = withStyles({
    root: {
      padding: '15px',
      width: '80px',
    },
  })(TableCell)

  const BodyRow = withStyles({
    root: {
      height: 200,
    },
  })(TableRow)

  const header = days.map((day, index) => (
    <DayCell key={`header-${index}`} align='center'>
      <Typography variant='button'>{day.name}</Typography>
    </DayCell>
  ))

  const lunches = days.map((day, index) => {
    let onCellClick
    if (onMealClick) {
      onCellClick = () => onMealClick(day.name.toLowerCase(), 'lunch', day.lunch.map(recipe => recipe.id))
    }

    return (
      <WeekRecipeCell
        key={`lunch-${index}`}
        recipes={day.lunch}
        onCellClick={onCellClick}
        dataTag={`lunch-${day.name}`}
      />
    )
  })

  const diners = days.map((day, index) => {
    let onCellClick
    if (onMealClick) {
      onCellClick = () => onMealClick(day.name.toLowerCase(), 'diner', day.diner.map(recipe => recipe.id))
    }

    return (
      <WeekRecipeCell
        key={`diner-${index}`}
        recipes={day.diner}
        onCellClick={onCellClick}
        dataTag={`diner-${day.name}`}
      />
    )
  })

  return (
    <Paper>
      <Table style={{ tableLayout: 'fixed' }}>
        <TableHead>
          <TableRow>
            <FirstColumnCell />
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

MultipleDayPane.propTypes = {
  days: PropTypes.array,
  onMealClick: PropTypes.func,
}
