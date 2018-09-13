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

  static setFavoriteRecipe(id, favorite) {
    let options = {
      method: 'PATCH',
      headers:{
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ favorite: favorite })
    }
    return fetch("/api/recipes/" + id, options);
  }
}
