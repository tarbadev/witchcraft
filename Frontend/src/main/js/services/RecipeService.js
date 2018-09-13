export default class RecipeService {
  static fetchRecipes() {
    return fetch("/api/recipes")
  		.then(function(response) {
  			return response.json();
  		});
  }

  static fetchRecipe(id) {
    return fetch("/api/recipes/" + id)
  		.then(function(response) {
  			return response.json();
  		})
      .catch(function(error) {
        return null;
      });
  }

  static deleteRecipe(id) {
    return fetch("/api/recipes/" + id, { method: 'delete' });
  }
}
