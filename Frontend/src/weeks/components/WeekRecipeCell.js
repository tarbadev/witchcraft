import React from 'react'
import PropTypes from 'prop-types'
import TableCell from '@material-ui/core/TableCell'

import './WeekRecipeCell.css'
import { RecipeCard } from 'src/recipes/components/RecipeCard'

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
      <RecipeCard title={recipe.name} imgUrl={recipe.imgUrl} />
      }
    </TableCell>
  )
}

WeekRecipeCell.propTypes = {
  recipe: PropTypes.object,
  dataTag: PropTypes.string,
  onCellClick: PropTypes.func,
}
