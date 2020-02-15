import PropTypes from 'prop-types'
import React from 'react'
import List from '@material-ui/core/List'
import { makeStyles } from '@material-ui/core'
import { Ingredient } from 'src/recipes/components/Ingredient'


const useStyles = makeStyles({
  listRoot: {
    paddingTop: 0,
    paddingBottom: 0,
    width: '100%',
  },
})

export const IngredientContainer = ({ ingredients, onIngredientDeletion, editable = true }) => {
  const classes = useStyles()

  const ingredientComponents = ingredients.map((ingredient, index) =>
    <Ingredient
      key={`ingredient-${ingredient.id}`}
      ingredient={ingredient}
      index={index}
      lastItem={index === ingredients.length - 1}
      deleteCallback={onIngredientDeletion}
      editable={editable}
    />,
  )

  return <List classes={{ root: classes.listRoot }}>
    {ingredientComponents}
  </List>
}

IngredientContainer.propTypes = {
  ingredients: PropTypes.array,
  onIngredientDeletion: PropTypes.func,
  editable: PropTypes.bool,
}