import React from 'react';
import { shallow } from 'enzyme';
import Grid from '@material-ui/core/Grid';

import Recipes from 'app-components/Recipes';
import RecipeList from 'app-components/RecipeList';
import RecipeSearch from 'app-components/RecipeSearch';

import RecipeService from 'app-services/RecipeService';

const promisedRecipeList = require('test-resources/recipeList.json');

describe("Recipes", function () {
  beforeEach(function() {
    spyOn(RecipeService, 'fetchRecipes').and.returnValue(Promise.resolve(promisedRecipeList));
  });

  it('renders without crashing', () => {
    const recipes = shallow(<Recipes />);
    expect(recipes).toBeDefined();
  });

  describe("Content", function() {
    beforeEach(async () => {
      this.instance = await shallow(<Recipes />);
    });

    it('fetches a list of recipes', () => {
      expect(RecipeService.fetchRecipes).toHaveBeenCalled();
      expect(this.instance.state('recipes')).toBe(promisedRecipeList.recipes);
      expect(this.instance.state('allRecipes')).toBe(promisedRecipeList.recipes);
    });

    it('is a Grid container with spacing', () => {
      expect(this.instance.is(Grid)).toBeTruthy();
      expect(this.instance.props().container).toBeTruthy();
      expect(this.instance.props().spacing).toBe(24);
    });

    it('contains a recipe search in a Grid item', () => {
      let grid = this.instance.findWhere(node => node.props().item).at(0);
      expect(grid.props().item).toBeTruthy();
      expect(grid.props().xs).toBe(12);

      expect(grid.find(RecipeSearch).length).toBe(1);
      expect(grid.find(RecipeSearch).at(0).props().onSearch).toBe(this.instance.instance().onSearch);
    });
  });

  describe("onSearch", function() {
    beforeEach(async () => {
      this.instance = await shallow(<Recipes />);
    });

    it('filters the list of recipes', () => {
      expect(this.instance.state('recipes').length).toBe(promisedRecipeList.recipes.length);
      this.instance.instance().onSearch({target: {value: 'pizza'}});
      expect(this.instance.state('recipes').length).toBe(1);

      this.instance.instance().onSearch({target: {value: ''}});
      expect(this.instance.state('recipes').length).toBe(2);
    });

    it('filters the list of recipes regardless of the case', () => {
      expect(this.instance.state('recipes').length).toBe(promisedRecipeList.recipes.length);
      this.instance.instance().onSearch({target: {value: 'PIZZA'}});
      expect(this.instance.state('recipes').length).toBe(1);

      this.instance.instance().onSearch({target: {value: ''}});
      expect(this.instance.state('recipes').length).toBe(2);
    });
  });
});
