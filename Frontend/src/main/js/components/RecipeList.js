import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import { Link } from "react-router-dom";

import styles from 'app-components/RecipeList.css';
import RecipeCard from './RecipeCard'
import RecipeService from 'app-services/RecipeService';

export default class RecipeList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      recipes: []
    };
  }

  componentDidMount() {
    RecipeService.fetchRecipes()
      .then((data) => {
        this.setState({
          recipes: data.recipes
        })
      });
  }

  render() {
    let recipeCards = this.state.recipes.map(recipe =>
      <Grid item xs={3} key={recipe.id}>
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
