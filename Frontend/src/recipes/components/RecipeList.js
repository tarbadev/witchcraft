import React from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import Box from '@material-ui/core/Box'
import { Link } from 'react-router-dom'

import 'src/recipes/components/RecipeList.css'
import { RecipeCard } from './RecipeCard'

export const RecipeList = ({ recipes = [] }) => {
  const recipeCards = recipes.map(recipe =>
    <Grid item lg={3} md={4} sm={6} key={recipe.id}>
      <Link to={`/recipes/${recipe.id}`} className="recipe-list__link">
        <Box width={1}>
          <RecipeCard
            imgUrl={recipe.imgUrl}
            title={recipe.name} />
        </Box>
      </Link>
    </Grid>,
  )

  return (
    <Grid container spacing={3}>
      {recipeCards}
    </Grid>
  )
}

RecipeList.propTypes = {
  recipes: PropTypes.array,
}
