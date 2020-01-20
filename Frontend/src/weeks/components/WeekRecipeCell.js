import React from 'react'
import PropTypes from 'prop-types'
import TableCell from '@material-ui/core/TableCell'

import './WeekRecipeCell.css'
import { RecipeCard } from 'src/recipes/components/RecipeCard'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import AddCircleOutlineOutlinedIcon from '@material-ui/icons/AddCircleOutlineOutlined'

export const WeekRecipeCell = ({ recipes, onCellClick, dataTag }) => {
  const recipeGrids = recipes.map(recipe => (
    <Grid item key={`grid-recipe-cell-${recipe.id}-${dataTag}`}>
      <RecipeCard
        title={recipe.name}
        imgUrl={recipe.imgUrl}
      />
    </Grid>
  ))

  const buttonContainerStyles = recipeGrids.length === 0 ? { flexGrow: 1 } : { paddingTop: '5px', flexGrow: 1 }

  return (
    <TableCell
      className={'table-cell'}
      data-meal={dataTag}
      style={{ verticalAlign: 'top' }}
    >
      <Grid container direction='column' alignItems='stretch' justify='flex-start' style={{ height: '100%' }}>
        {recipeGrids}
        {onCellClick &&
        <Grid
          item
          style={buttonContainerStyles}
        >
          <Button
            fullWidth
            style={{ height: '100%' }}
            onClick={onCellClick}
            data-add-recipe
          >
            <AddCircleOutlineOutlinedIcon color='action' fontSize='large' />
          </Button>
        </Grid>}
      </Grid>
    </TableCell>
  )
}

WeekRecipeCell.propTypes = {
  recipes: PropTypes.array,
  dataTag: PropTypes.string,
  onCellClick: PropTypes.func,
}
