import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import { Link } from "react-router-dom";

import styles from 'app-components/RecipeList.css';
import RecipeCard from './RecipeCard'
import RecipeService from 'app-services/RecipeService';

export default class RecipeList extends Component {
  render() {
    let recipeCards = this.props.recipes.map(recipe =>
      <Grid item lg={3} md={4} sm={6} key={recipe.id}>
        <Link to={recipe.url} className={styles.link}>
          <RecipeCard
          imgUrl={recipe.imgUrl}
          title={recipe.name} />
        </Link>
      </Grid>
    );

    return (
      <Grid container spacing={24}>
        {recipeCards}
      </Grid>
    );
  }
}

RecipeList.defaultProps = {
    recipes: []
 };
