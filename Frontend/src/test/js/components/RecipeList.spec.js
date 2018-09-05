import React from 'react';
import { shallow } from 'enzyme';
import Grid from '@material-ui/core/Grid';
import { Link } from "react-router-dom";

import styles from 'app-components/RecipeList.css';
import RecipeList from 'app-components/RecipeList';
import RecipeCard from 'app-components/RecipeCard';
import RecipeService from 'app-services/RecipeService';

const promisedRecipeList = require('test-resources/recipeList.json');

describe("RecipeList", function () {
  beforeEach(function() {
    spyOn(RecipeService, 'fetchRecipes').and.returnValue(Promise.resolve(promisedRecipeList));
  });

  it('renders without crashing', () => {
    const recipeList = shallow(<RecipeList />);
    expect(recipeList).toBeDefined();
  });

  describe("Content", function() {
    beforeEach(async () => {
      this.instance = await shallow(<RecipeList />);
      this.instance.update();
    });

    it('is a Grid and has spacing', () => {
      expect(this.instance.is(Grid)).toBeTruthy();
      expect(this.instance.props().container).toBeTruthy();
      expect(this.instance.props().spacing).toBe(24);
    });

    it('fetches a list of recipes', () => {
      expect(RecipeService.fetchRecipes).toHaveBeenCalled();
      expect(this.instance.state('recipes')).toBe(promisedRecipeList.recipes);
    });

    it('renders a RecipeCard for each recipe in a Grid', () => {
      let grids = this.instance.findWhere(node => node.props().item);
      expect(grids.length).toBe(promisedRecipeList.recipes.length);

      let index = 0;
      grids.map((grid) => {
        expect(grid.props().xs).toBe(3);
        expect(grid.find(Link).length).toBe(1);

        let link = grid.find(Link).at(0);
        expect(link.props().to).toBe(promisedRecipeList.recipes[index].url);
        expect(link.props().className).toBe(styles.link);
        expect(link.find(RecipeCard).length).toBe(1);

        index++;
      });
    });

    it('renders a RecipeCard with an imgUrl prop', () => {
      expect(this.instance.find(RecipeCard).get(0).props.imgUrl).toBe(promisedRecipeList.recipes[0].imgUrl);
    });

    it('renders a RecipeCard with a title prop', () => {
      expect(this.instance.find(RecipeCard).get(0).props.title).toBe(promisedRecipeList.recipes[0].name);
    });
  });
});
