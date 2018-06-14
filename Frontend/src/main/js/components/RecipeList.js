import React, { Component } from 'react';
import RecipeService from 'app-services/RecipeService';

export default class RecipeList extends Component {
  componentDidMount() {
    RecipeService.fetchRecipes()
      .then((recipes) => {
        this.setState({
          recipes: recipes
        })
      });
  }

  render() {
    return (
      <div>
        ...
      </div>
    );
  }
}
