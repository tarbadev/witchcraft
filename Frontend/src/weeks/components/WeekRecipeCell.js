import React from 'react'
import PropTypes from 'prop-types'
import TableCell from '@material-ui/core/TableCell'

import './WeekRecipeCell.css'
import { RecipeCard } from 'src/recipes/components/RecipeCard'
import Grid from '@material-ui/core/Grid'

export const WeekRecipeCell = ({ recipes, onCellClick, dataTag }) => {
  const classes = onCellClick
    ? 'table-cell table-cell-clickable'
    : 'table-cell'

  const recipeGrids = recipes.map(recipe => (
    <Grid item key={`grid-recipe-cell-${recipe.id}`}>
      <RecipeCard
        title={recipe.name}
        imgUrl={recipe.imgUrl}
      />
    </Grid>
  ))
  return (
    <TableCell
      className={classes}
      onClick={onCellClick}
      data-meal={dataTag}
    >
      {onCellClick && <div className='hover-mask' />}
      <Grid container>
        {recipeGrids}
      </Grid>
    </TableCell>
  )
}

WeekRecipeCell.propTypes = {
  recipes: PropTypes.array,
  dataTag: PropTypes.string,
  onCellClick: PropTypes.func,
}
