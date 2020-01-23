import React from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import Box from '@material-ui/core/Box'
import { Link } from 'react-router-dom'

import { RecipeCard } from './RecipeCard'

export const RecipeList = ({ recipes = [] }) => {
  const recipeCards = recipes.map(recipe =>
    <Grid item xs={12} sm={4} md={3} lg={2} key={recipe.id}>
      <Link to={`/recipes/${recipe.id}`} className='recipe-list__link'>
        <Box width={1}>
          <RecipeCard
            imgUrl={recipe.imgUrl}
            title={recipe.name} />
        </Box>
      </Link>
    </Grid>,
  )

  return (
    <Grid container spacing={3} direction='row' alignItems='stretch'>
      {recipeCards}
    </Grid>
  )
}

RecipeList.propTypes = {
  recipes: PropTypes.array,
}
