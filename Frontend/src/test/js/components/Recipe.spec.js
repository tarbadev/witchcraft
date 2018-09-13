import React from 'react';
import { shallow } from 'enzyme';
import { Redirect } from 'react-router-dom'
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import CircularProgress from '@material-ui/core/CircularProgress';
import DeleteIcon from '@material-ui/icons/Delete'
import OpenInNewIcon from '@material-ui/icons/OpenInNew'

import styles from 'app-components/Recipe.css';
import Recipe from 'app-components/Recipe';
import Step from 'app-components/Step';
import Ingredient from 'app-components/Ingredient';

import RecipeService from 'app-services/RecipeService';
const recipeList = require('test-resources/recipeList.json');
const promisedRecipe = recipeList.recipes[0];

describe("Recipe", function () {
  describe("on fetch returns null", function () {
    beforeEach(function() {
      spyOn(RecipeService, 'fetchRecipe').and.returnValue(null);
    });

    it('renders without crashing and redirects to /recipes', async () => {
      const recipe = await shallow(<Recipe match={{params: {id: 1}}} />);
      expect(recipe).toBeDefined();

      expect(recipe.find(Redirect).length).toBe(1);
      expect(recipe.find(Redirect).at(0).props().to).toBe("/recipes");
    });
  });

  describe("on fetch returns a promise with null body", function () {
    beforeEach(function() {
      spyOn(RecipeService, 'fetchRecipe').and.returnValue(Promise.resolve(null));
    });

    it('renders without crashing and redirects to /recipes', async () => {
      const recipe = await shallow(<Recipe match={{params: {id: 1}}} />);
      expect(recipe).toBeDefined();

      expect(recipe.find(Redirect).length).toBe(1);
      expect(recipe.find(Redirect).at(0).props().to).toBe("/recipes");
    });
  });

  describe("on fetch success", function () {
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

      it('contains a Grid item to display the recipe title and delete button', () => {
        let gridTitle = this.instance.findWhere(node => node.props().name === "title").at(0);
        expect(gridTitle.props().item).toBeTruthy();
        expect(gridTitle.props().sm).toBe(12);

        let gridContainer = gridTitle.children().findWhere(node => node.props().container).at(0);
        expect(gridContainer.props().justify).toBe("space-between");
        expect(gridContainer.children().find(Grid).length).toBe(2);

        let grid1 = gridContainer.children().find(Grid).at(0);
        expect(grid1.props().sm).toBe(10);
        expect(grid1.props().sm).toBe(10);
        expect(grid1.find(Typography).length).toBe(1);

        let title = gridTitle.find(Typography).at(0);
        expect(title.props().className).toBe(styles.title);
        expect(title.props().variant).toBe("headline");
        expect(title.children().text()).toBe(promisedRecipe.name);

        let grid2 = gridContainer.children().find(Grid).at(1);
        expect(grid2.find(Button).length).toBe(1);
        expect(grid2.props().className).toBe(styles.circularProgressContainer);

        let deleteButton = grid2.find(Button).at(0);
        expect(deleteButton.props().variant).toBe("contained");
        expect(deleteButton.props().onClick).toBe(this.instance.instance().onDeleteButtonClick);
        expect(deleteButton.props().className).toBe(styles.deleteButton);

        expect(deleteButton.find(DeleteIcon).length).toBe(1);
        expect(deleteButton.find(DeleteIcon).at(0).props().className).toBe(styles.deleteIcon);

        expect(deleteButton.children().at(1).text()).toBe("Delete");
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

    describe(".onDeleteButtonClick", function() {
      let promiseHelper;
      let id = 1;

      beforeEach(async () => {
        this.instance = await shallow(<Recipe match={{params: {id: id}}} />);

        let deletePromise = new Promise(function(resolve, reject) {
    			promiseHelper = {
    				resolve: resolve,
    				reject: reject
    			};
    		});
        spyOn(RecipeService, 'deleteRecipe').and.returnValue(deletePromise);;
      });

      it('deletes the current recipe and redirects to /recipes when response is ok', async () => {
        this.instance.instance().onDeleteButtonClick();

        expect(this.instance.state('redirect')).toBeFalsy();
        expect(RecipeService.deleteRecipe).toHaveBeenCalledWith(id);

        await promiseHelper.resolve({ok:true});

        this.instance.update();
        expect(this.instance.state('redirect')).toBeTruthy();
        expect(this.instance.find(Redirect).length).toBe(1);
        expect(this.instance.find(Redirect).at(0).props().to).toBe("/recipes");
      });

      it('redirects to /recipes when response is not ok', async () => {
        this.instance.instance().onDeleteButtonClick();

        expect(this.instance.state('redirect')).toBeFalsy();
        expect(RecipeService.deleteRecipe).toHaveBeenCalledWith(id);

        await promiseHelper.resolve({ok:false});

        this.instance.update();
        expect(this.instance.state('redirect')).toBeTruthy();
        expect(this.instance.find(Redirect).length).toBe(1);
        expect(this.instance.find(Redirect).at(0).props().to).toBe("/recipes");
      });

      it('displays a CircularProgress when function is called', () => {
        expect(this.instance.state('isDeleting')).toBeFalsy();
        let deleteButton = this.instance.findWhere(node => node.props().className == styles.deleteButton).at(0);
        expect(deleteButton.find(CircularProgress).length).toBe(0);
        expect(deleteButton.props().disabled).toBeFalsy();

        deleteButton.simulate('click');
        this.instance.update();
        deleteButton = this.instance.findWhere(node => node.props().className == styles.deleteButton).at(0);

        expect(this.instance.state('isDeleting')).toBeTruthy();
        expect(deleteButton.props().disabled).toBeTruthy();
        expect(this.instance.find(CircularProgress).length).toBe(1);
        let progress = this.instance.find(CircularProgress).at(0);
        expect(progress.props().size).toBe(24);
        expect(progress.props().className).toBe(styles.circularProgress);
      });

      it('hides CircularProgress when promise is resolved', async () => {
        expect(this.instance.state('isDeleting')).toBeFalsy();
        let deleteButton = this.instance.findWhere(node => node.props().className == styles.deleteButton).at(0);
        expect(deleteButton.props().disabled).toBeFalsy();

        deleteButton.simulate('click');
        this.instance.update();
        deleteButton = this.instance.findWhere(node => node.props().className == styles.deleteButton).at(0);

        expect(this.instance.state('isDeleting')).toBeTruthy();
        expect(deleteButton.props().disabled).toBeTruthy();
        expect(this.instance.find(CircularProgress).length).toBe(1);

        await promiseHelper.resolve({ok:true});

        this.instance.update();
        deleteButton = this.instance.findWhere(node => node.props().className == styles.deleteButton).at(0);

        expect(this.instance.state('isDeleting')).toBeFalsy();
        expect(deleteButton.props().disabled).toBeFalsy();
        expect(this.instance.find(CircularProgress).length).toBe(0);
      });
    });
  });
});
