export default class RecipeService {
  static fetchRecipes() {
    return fetch("/api/recipes")
  		.then(function(response) {
  			return response.json();
  		});
  }
}
