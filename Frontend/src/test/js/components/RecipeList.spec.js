import React from 'react';
import { shallow } from 'enzyme';

import RecipeList from 'app-components/RecipeList';
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
    it('fetches a list of recipes', async () => {
      const recipeList = await shallow(<RecipeList />);
      expect(RecipeService.fetchRecipes).toHaveBeenCalled();
      expect(recipeList.state('recipes')).toBe(promisedRecipeList);
    });
  });
});
