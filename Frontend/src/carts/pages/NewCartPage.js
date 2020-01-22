import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import Switch from '@material-ui/core/Switch'
import FormGroup from '@material-ui/core/FormGroup'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import { createCart } from 'src/carts/actions/NewCartActions'
import { PageTitle } from 'src/app/components/PageTitle'
import { useAppContext } from 'src/app/components/StoreProvider'
import { getAllRecipes } from 'src/recipes/actions/RecipesActions'
import { CART } from 'src/app/components/Header'

export const NewCartPageContainer = ({ history }) => {
  const { state, dispatch, setCurrentHeader } = useAppContext()
  setCurrentHeader(CART)

  const [recipes, setRecipes] = useState(state.recipes)

  useEffect(() => dispatch(getAllRecipes(data => setRecipes(data.recipes))), [])

  return <NewCartPage
    recipes={recipes}
    generateCart={recipeIds => dispatch(createCart(recipeIds, cart => history.push(`/carts/${cart.id}`)))} />
}

NewCartPageContainer.propTypes = {
  history: PropTypes.object,
}

export const NewCartPage = ({
  recipes,
  generateCart,
}) => {
  const [selectedRecipes, setSelectedRecipes] = useState([])

  const recipeList = recipes.map((recipe, index) => (
    <FormControlLabel
      key={recipe.id}
      control={
        <Switch
          className={`new-cart-page__switch-recipe-${index}`}
          onChange={() => setSelectedRecipes([...selectedRecipes, recipe.id])}
          value={`${recipe.id}`}
          checked={selectedRecipes.indexOf(recipe.id) >= 0}
          color='primary'
        />
      }
      label={recipe.name}
    />
  ))

  return (
    <Grid container spacing={3}>
      <PageTitle title='New Cart' />
      <Grid item xs={12}>
        <FormGroup>
          {recipeList}
        </FormGroup>
      </Grid>
      <Grid item xs={12}>
        <Button
          variant='contained'
          color='primary'
          href=''
          className='new-cart-page__generate-button'
          onClick={() => generateCart(selectedRecipes)}>
          Generate
        </Button>
      </Grid>
    </Grid>
  )
}

NewCartPage.propTypes = {
  recipes: PropTypes.array,
  generateCart: PropTypes.func,
}