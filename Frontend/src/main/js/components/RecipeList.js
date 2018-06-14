import React, { Component } from 'react';
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
    let recipeCards = this.state.recipes.map(recipe => <RecipeCard key={recipe.id} />);

    return (
      <div>
        {recipeCards}
      </div>
    );
  }
}
