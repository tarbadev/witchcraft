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

  describe(".fetchRecipe()", function () {
  	let recipePromise;
  	let promiseHelper;
  	let id = 33;

    beforeEach(function() {
      let fetchPromise = new Promise(function(resolve, reject) {
  			promiseHelper = {
  				resolve: resolve,
  				reject: reject
  			};
  		});

  		spyOn(window, 'fetch').and.returnValue(fetchPromise);
  		recipePromise = RecipeService.fetchRecipe(id);
  	});

    it('calls the witchcraft API', () => {
      expect(window.fetch).toHaveBeenCalledWith('/api/recipes/' + id);
    });

    it('returns a promise', () => {
      expect(recipePromise).toEqual(jasmine.any(Promise));
  	});

    describe('on successful fetch', function() {
      beforeEach(function() {
        let responseJson = JSON.stringify(promisedRecipeList.recipes[0]);
  			let response = new Response(responseJson);
  			promiseHelper.resolve(response);
  		});

      it('resolves its promise with the current recipes list', function(done) {
  			recipePromise.then(function(recipes) {
  				expect(recipes).toEqual(promisedRecipeList.recipes[0]);
  				done();
  			});
  		});
    });

    describe('on unsuccessful fetch', function() {
      beforeEach(function() {
  			promiseHelper.resolve();
  		});

      it('catches the error and returns null', function(done) {
  			recipePromise.then(function(response) {
  				expect(response).toBeNull();
  				done();
  			});
  		});
    });
	});

  describe(".deleteRecipe()", function () {
    let id = 33;
    let recipePromise;
  	let promiseHelper;

    beforeEach(function() {
      let deletePromise = new Promise(function(resolve, reject) {
  			promiseHelper = {
  				resolve: resolve,
  				reject: reject
  			};
  		});

      spyOn(window, 'fetch').and.returnValue(deletePromise);

      recipePromise = RecipeService.deleteRecipe(id);
  	});

    it('calls the witchcraft API', () => {
      expect(window.fetch).toHaveBeenCalledWith('/api/recipes/' + id, {method: 'delete'});
    });

    it('returns a promise', () => {
      expect(recipePromise).toEqual(jasmine.any(Promise));
  	});
  });

  describe(".setFavoriteRecipe()", function () {
    let id = 33;
    let favorite = true;
    let recipePromise;
  	let promiseHelper;

    beforeEach(function() {
      let setFavoritePromise = new Promise(function(resolve, reject) {
  			promiseHelper = {
  				resolve: resolve,
  				reject: reject
  			};
  		});

      spyOn(window, 'fetch').and.returnValue(setFavoritePromise);

      recipePromise = RecipeService.setFavoriteRecipe(id, favorite);
  	});

    it('calls the witchcraft API', () => {
      let options = {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ favorite: favorite })
      }
      expect(window.fetch).toHaveBeenCalledWith('/api/recipes/' + id, options);
    });

    it('returns a promise', () => {
      expect(recipePromise).toEqual(jasmine.any(Promise));
  	});
  });
});
