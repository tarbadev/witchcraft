import React from 'react';
import { shallow } from 'enzyme';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import OpenInNewIcon from '@material-ui/icons/OpenInNew'

import styles from 'app-components/Recipe.css';
import Recipe from 'app-components/Recipe';
import Step from 'app-components/Step';
import Ingredient from 'app-components/Ingredient';

import RecipeService from 'app-services/RecipeService';
const recipeList = require('test-resources/recipeList.json');
const promisedRecipe = recipeList.recipes[0];

describe("Recipe", function () {
  beforeEach(function() {
    spyOn(RecipeService, 'fetchRecipe').and.returnValue(Promise.resolve(promisedRecipe));
  });

  it('renders without crashing', () => {
    const recipe = shallow(<Recipe match={{params: {id: 1}}} />);
    expect(recipe).toBeDefined();
  });

  describe("Content", function() {
    beforeEach(async () => {
      this.instance = await shallow(<Recipe match={{params: {id: 1}}} />);
    });

    it('fetches a recipe', () => {
      expect(RecipeService.fetchRecipe).toHaveBeenCalled();
      expect(this.instance.state('recipe')).toBe(promisedRecipe);
    });

    it('is a Grid container', () => {
      expect(this.instance.is(Grid)).toBeTruthy();
      expect(this.instance.props().container).toBeTruthy();
      expect(this.instance.props().spacing).toBe(24);
    });

    it('contains a Grid item to display the recipe title', () => {
      let gridTitle = this.instance.findWhere(node => node.props().name === "title").at(0);
      expect(gridTitle.props().item).toBeTruthy();
      expect(gridTitle.props().sm).toBe(12);
      expect(gridTitle.find(Typography).length).toBe(1);

      let title = gridTitle.find(Typography).at(0);
      expect(title.props().className).toBe(styles.title);
      expect(title.props().variant).toBe("headline");
      expect(title.children().text()).toBe(promisedRecipe.name);
    });

    it('contains a Grid item to display the recipe url', () => {
      let gridTopBar = this.instance.findWhere(node => node.props().name === "top-bar").at(0);
      expect(gridTopBar.props().item).toBeTruthy();
      expect(gridTopBar.props().sm).toBe(12);
      expect(gridTopBar.props().container).toBeTruthy();
      expect(gridTopBar.props().justify).toBe("space-between");
      expect(gridTopBar.children().findWhere(node => node.props().item).length).toBe(2);

      let recipeUrlButton = gridTopBar.children()
        .findWhere(node => node.props().item)
          .at(1)
        .find(Button)
          .at(0);
      expect(recipeUrlButton.props().variant).toBe("contained");
      expect(recipeUrlButton.props().size).toBe("small");
      expect(recipeUrlButton.props().target).toBe("_blank");
      expect(recipeUrlButton.props().href).toBe(promisedRecipe.originUrl);
      expect(recipeUrlButton.children().at(1).text()).toBe("Go to recipe");

      expect(recipeUrlButton.find(OpenInNewIcon).length).toBe(1);
      expect(recipeUrlButton.find(OpenInNewIcon).at(0).props().className).toBe(styles.leftIcon);
    });

    it('contains a Grid item to display the recipe image', () => {
      let gridImage = this.instance.findWhere(node => node.props().name === "image").at(0);
      expect(gridImage.props().item).toBeTruthy();
      expect(gridImage.props().sm).toBe(3);
      expect(gridImage.find("img").length).toBe(1);

      let img = gridImage.find("img").at(0);
      expect(img.props().src).toBe(promisedRecipe.imgUrl);
      expect(img.props().className).toBe(styles.image);
    });

    it('contains a Grid item to display the recipe steps', () => {
      let gridSteps = this.instance.findWhere(node => node.props().name === "steps").at(0);
      expect(gridSteps.props().item).toBeTruthy();
      expect(gridSteps.props().sm).toBe(5);

      let title = gridSteps.find(Typography).at(0);
      expect(title.props().variant).toBe("title");
      expect(title.props().gutterBottom).toBeTruthy();
      expect(title.children().text()).toBe("Steps");

      let gridContainer = gridSteps.findWhere(node => node.props().container).at(0);
      expect(gridContainer.props().alignItems).toBe("baseline");
      expect(gridContainer.props().spacing).toBe(8);
      expect(gridContainer.children().find(Grid).length).toBe(promisedRecipe.steps.length);

      let grids = gridContainer.children().find(Grid);
      promisedRecipe.steps.map((step, index) => {
        let grid = grids.at(index);
        expect(grid.props().item).toBeTruthy();
        expect(grid.props().sm).toBe(12);

        let stepComponent = grid.find(Step).at(0);
        expect(stepComponent.props().number).toBe(index + 1);
        expect(stepComponent.props().step).toBe(step.name);
      });
    });

    it('contains a Grid item to display the recipe ingredients', () => {
      let gridIngredients = this.instance.findWhere(node => node.props().name === "ingredients").at(0);
      expect(gridIngredients.props().item).toBeTruthy();
      expect(gridIngredients.props().sm).toBe(4);

      let title = gridIngredients.find(Typography).at(0);
      expect(title.props().variant).toBe("title");
      expect(title.props().gutterBottom).toBeTruthy();
      expect(title.children().text()).toBe("Ingredients");

      let gridContainer = gridIngredients.findWhere(node => node.props().container).at(0);
      expect(gridContainer.props().alignItems).toBe("baseline");
      expect(gridContainer.props().spacing).toBe(8);
      expect(gridContainer.children().find(Grid).length).toBe(promisedRecipe.ingredients.length);

      let grids = gridContainer.children().find(Grid);
      promisedRecipe.ingredients.map((ingredient, index) => {
        let grid = grids.at(index);
        expect(grid.props().item).toBeTruthy();
        expect(grid.props().sm).toBe(12);

        let ingredientComponent = grid.find(Ingredient).at(0);
        expect(ingredientComponent.props().unit).toBe(ingredient.unit);
        expect(ingredientComponent.props().quantity).toBe(ingredient.quantity);
        expect(ingredientComponent.props().ingredient).toBe(ingredient.name);
      });
    });
  });
});
