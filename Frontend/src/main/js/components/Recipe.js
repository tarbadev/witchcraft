import React, {Component} from 'react';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import OpenInNewIcon from '@material-ui/icons/OpenInNew'

import styles from 'app-components/Recipe.css';
import Step from 'app-components/Step';
import Ingredient from 'app-components/Ingredient';

import RecipeService from 'app-services/RecipeService';

export default class Recipe extends Component {
  constructor(props) {
    super(props);
    this.state = {
      recipe: {}
    };
  }

  componentDidMount() {
    RecipeService.fetchRecipe(this.props.match.params.id).then((data) => {
      this.setState({recipe: data})
    });
  }

  render() {
    let steps;
    let ingredients;

    if (this.state.recipe.steps) {
      steps = this.state.recipe.steps.map((step, index) => (
        <Grid item key={step.id} sm={12}>
          <Step number={index + 1} step={step.name} />
        </Grid>
      ));
    }

    if (this.state.recipe.ingredients) {
      ingredients = this.state.recipe.ingredients.map((ingredient, index) => (
        <Grid item key={ingredient.id} sm={12}>
          <Ingredient
            ingredient={ingredient.name}
            unit={ingredient.unit}
            quantity={ingredient.quantity}/>
        </Grid>
      ));
    }

    return (
      <Grid container spacing={24}>
        <Grid item sm={12} name="title">
          <Typography variant="headline" className={styles.title}>
            {this.state.recipe.name}
          </Typography>
        </Grid>
        <Grid item sm={12} name="top-bar" container justify="space-between">
          <Grid item></Grid>
          <Grid item>
            <Button
              target="_blank"
              variant="contained"
              size="small"
              href={this.state.recipe.originUrl}>
              <OpenInNewIcon className={styles.leftIcon} />
              Go to recipe
            </Button>
          </Grid>
        </Grid>
        <Grid item sm={3} name="image">
          <img src={this.state.recipe.imgUrl} className={styles.image}/>
        </Grid>
        <Grid item sm={5} name="steps">
          <Typography variant="title" gutterBottom>Steps</Typography>
          <Grid
            container
            alignItems="baseline"
            spacing={8}>
            {steps}
          </Grid>
        </Grid>
        <Grid item sm={4} name="ingredients">
          <Typography variant="title" gutterBottom>Ingredients</Typography>
          <Grid
            container
            alignItems="baseline"
            spacing={8}>
            {ingredients}
          </Grid>
        </Grid>
      </Grid>
    )
  }
}
