import React from 'react'
import PropTypes from 'prop-types'
import { withStyles } from '@material-ui/core/styles'
import Paper from '@material-ui/core/Paper'
import TableCell from '@material-ui/core/TableCell'
import Typography from '@material-ui/core/Typography'

import './WeekRecipeCell.css'

const RecipePaper = withStyles({
  root: {
    'padding': '5px',
  },
})(Paper)

export const WeekRecipeCell = ({ recipe, onCellClick, dataTag }) => {
  const classes = onCellClick
    ? 'table-cell table-cell-clickable'
    : 'table-cell'
  return (
    <TableCell
      className={classes}
      onClick={onCellClick}
      data-meal={dataTag}
    >
      {onCellClick && <div className='hover-mask' />}
      {(recipe && recipe.name) &&
      <RecipePaper>
        <Typography>{recipe.name}</Typography>
      </RecipePaper>
      }
    </TableCell>
  )
}

WeekRecipeCell.propTypes = {
  recipe: PropTypes.object,
  dataTag: PropTypes.string,
  onCellClick: PropTypes.func,
}
