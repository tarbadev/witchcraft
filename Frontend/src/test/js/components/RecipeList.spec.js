import React from 'react';
import { shallow } from 'enzyme';

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

    it('fetches a list of recipes', () => {
      expect(RecipeService.fetchRecipes).toHaveBeenCalled();
      expect(this.instance.state('recipes')).toBe(promisedRecipeList.recipes);
    });

    it('renders a RecipeCard for each recipe', () => {
      expect(this.instance.find(RecipeCard).length).toBe(promisedRecipeList.recipes.length);
    });
  });
});
