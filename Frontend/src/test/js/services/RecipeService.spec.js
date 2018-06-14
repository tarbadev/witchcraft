import RecipeService from 'app-services/RecipeService';

const promisedRecipeList = require('test-resources/recipeList.json');

describe("RecipeService", function () {
  describe(".fetchRecipes()", function () {
  	let recipesPromise;
  	let promiseHelper;

    beforeEach(function() {
      let fetchPromise = new Promise(function(resolve, reject) {
  			promiseHelper = {
  				resolve: resolve,
  				reject: reject
  			};
  		});

  		spyOn(window, 'fetch').and.returnValue(fetchPromise);
  		recipesPromise = RecipeService.fetchRecipes();
  	});

    it('calls the witchcraft API', () => {
      expect(window.fetch).toHaveBeenCalledWith('/api/recipes');
    });

    it('returns a promise', () => {
      expect(recipesPromise).toEqual(jasmine.any(Promise));
  	});

    describe('on successful fetch', function() {
      beforeEach(function() {
        let responseJson = JSON.stringify(promisedRecipeList);
  			let response = new Response(responseJson);
  			promiseHelper.resolve(response);
  		});

      it('resolves its promise with the current recipes list', function(done) {
  			recipesPromise.then(function(recipes) {
  				expect(recipes).toEqual(promisedRecipeList);
  				done();
  			});
  		});
    });

    describe('on unsuccessful fetch', function() {
  		var errorObj = { msg: 'An error occured while retrieving recipes list' };

  		beforeEach(function() {
  			promiseHelper.reject(errorObj);
  		});

  		it('resolves its promise with the current recipes list', function(done) {
  			recipesPromise.catch(function(error) {
  				expect(error).toEqual(errorObj);
  				done();
  			});
  		});
  	});
  });
});
