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

    it('renders a RecipeCard with an imgUrl prop', () => {
      expect(this.instance.find(RecipeCard).get(0).props.imgUrl).toBe(promisedRecipeList.recipes[0].imgUrl);
    });

    it('renders a RecipeCard with a title prop', () => {
      expect(this.instance.find(RecipeCard).get(0).props.title).toBe(promisedRecipeList.recipes[0].name);
    });

    it('renders a RecipeCard with a url prop', () => {
      expect(this.instance.find(RecipeCard).get(0).props.url).toBe(promisedRecipeList.recipes[0].url);
    });

    it('has classes "ui link cards list"', () => {
      expect(this.instance.hasClass('ui link cards list')).toBeTruthy();
    });
  });
});
