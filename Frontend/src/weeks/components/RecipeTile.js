import React from 'react'
import PropTypes from 'prop-types'
import GridListTile from '@material-ui/core/GridListTile'
import GridListTileBar from '@material-ui/core/GridListTileBar'

import './RecipeTile.css'

export const RecipeTile = ({
  recipe,
  onTileClick,
  children,
}) => {
  return (
    <GridListTile className='recipe-card' onClick={() => onTileClick(recipe)}>
      <img src={recipe.imgUrl} alt={recipe.name} />
      <GridListTileBar title={recipe.name} className='recipe-card-title' />
      {children && children}
    </GridListTile>
  )
}

RecipeTile.propTypes = {
  recipe: PropTypes.object,
  onTileClick: PropTypes.func,
  children: PropTypes.object,
}
