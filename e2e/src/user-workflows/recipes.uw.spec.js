import * as RecipesPage from '../page-objects/recipes.po'

describe('Recipes', () => {
  describe('Recipe List', () => {
    it('displays a recipe list', async () => {
      await RecipesPage.goTo()
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)
    })
  })

  describe('Search', () => {
    it('displays filtered recipes', async () => {
      await RecipesPage.goTo()
      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'tartiflette',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)

      await RecipesPage.search('thai')

      const filteredRecipes = await RecipesPage.getRecipes()
      expect(filteredRecipes).toEqual(['thai chicken salad'])
    })
  })
})