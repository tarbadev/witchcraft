import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';

import RecipeList from './RecipeList'
import RecipeSearch from './RecipeSearch'

import RecipeService from 'app-services/RecipeService';

export default class Recipes extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allRecipes: [],
      recipes: []
    };
    this.onSearch = this.onSearch.bind(this);
    this.onNewRecipeClick = this.onNewRecipeClick.bind(this);
  }

  componentDidMount() {
    RecipeService.fetchRecipes()
      .then((data) => {
        this.setState({
          recipes: data.recipes,
          allRecipes: data.recipes
        })
      });
  }

  onSearch(event) {
    let search = event.target.value;

    this.setState((previousState, currentProps) => {
      let filteredRecipes = previousState.allRecipes.filter(function(recipe) {
        return recipe.name.toLowerCase().includes(search.toLowerCase());
      });

      return { recipes: filteredRecipes };
    });
  }

  onNewRecipeClick(event) {
    this.props.history.push('/recipes/new');
  }

  render() {
    return (
        <Grid container spacing={24}>
          <Grid item xs={12}>
            <RecipeSearch onSearch={this.onSearch} />
          </Grid>
          <Grid item xs={12}>
            <Button variant="contained" color="primary" onClick={this.onNewRecipeClick}>
              New Recipe
            </Button>
          </Grid>
          <Grid item>
            <RecipeList recipes={this.state.recipes} />
          </Grid>
        </Grid>
    )
  }
}
