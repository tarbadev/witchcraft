import * as RecipePage from '../page-objects/recipe.po'
import * as RecipesPage from '../page-objects/recipes.po'
import * as EditRecipePage from '../page-objects/editRecipe.po'

describe('Recipe', () => {
  it('displays the ingredient details', async () => {
    await RecipePage.goTo(3, 'Thai Chicken Salad')

    const ingredient = await RecipePage.getIngredient(2)
    const expectedIngredient = {
      name: 'Cooked, shredded chicken breast',
      quantity: '2 cup',
    }

    expect(ingredient).toEqual(expectedIngredient)
  })

  it('displays the steps', async () => {
    await RecipePage.goTo(3, 'Thai Chicken Salad')

    const steps = await RecipePage.getSteps()
    const expectedSteps = [
      'In a large bowl, combine the Napa cabbage, red cabbage, carrot, green onion, cilantro and chicken breast. Toss with the dressing. Garnish with the toasted almonds. Serve.',
      'In a small glass bowl, combine the lime juice, peanut butter, soy sauce, agave nectar, fish sauce, rice vinegar and chili garlic sauce. Whisk until smooth.',
    ]
    expect(steps).toEqual(expectedSteps)
  })

  describe('on heart button click', () => {
    it('adds the recipe as favourite', async () => {
      await RecipePage.goTo(3, 'Thai Chicken Salad')

      expect(await RecipePage.isFavorite()).toBeFalsy()

      await RecipePage.clickOnFavoriteButton()
      await RecipePage.waitForFavoriteState()

      expect(await RecipePage.isFavorite()).toBeTruthy()
    })
  })

  describe('on delete button click', () => {
    it('displays a confirmation popup', async () => {
      await RecipePage.goTo(2, 'Tartiflette')

      await RecipePage.clickOnDeleteButton()
      const confirmationDeleteRecipe = await RecipePage.getConfirmationDeleteRecipe()
      expect(confirmationDeleteRecipe).toBe('Delete recipe Tartiflette?')
    })

    it('delete the recipe and redirect to recipe list', async () => {
      await RecipePage.goTo(2, 'Tartiflette')

      await RecipePage.clickOnDeleteButton()
      await RecipePage.clickOnConfirmDeleteButton()
      await RecipesPage.waitForPageLoaded()

      expect(global.page.url()).toBe(`${appUrl}/recipes`)

      const recipes = await RecipesPage.getRecipes()
      const expectedRecipes = [
        'lasagna alla bolognese',
        'thai chicken salad',
      ]
      expect(recipes).toEqual(expectedRecipes)
    })
  })

  describe('on modify button click', () => {
    it('redirects to edit recipe page', async () => {
      await RecipePage.goTo(3, 'Thai Chicken Salad')

      await RecipePage.clickOnModifyButton()
      await EditRecipePage.waitForPageLoaded()

      expect(global.page.url()).toBe(`${appUrl}/recipes/3/edit`)
    })
  })

  describe('on ingredient click', () => {
    it('displays the modify ingredient form and stores new values', async () => {
      await RecipePage.goTo(3, 'Thai Chicken Salad')

      const ingredients = await RecipePage.getIngredients()
      const expectedIngredients = [
        'Napa cabbage, thinly sliced (about 4 cups)',
        'Small red cabbage, thinly sliced (about 2 cups)',
        'Medium carrots, grated (about 1 cup)',
        'Green onion, thinly sliced',
        'Minced cilantro',
        'Cooked, shredded chicken breast',
        'Slivered almonds, toasted',
        'Lime, juiced',
        'Natural peanut butter',
        'Low-sodium soy sauce',
        'Agave nectar (or honey)',
        'Fish sauce',
        'Rice vinegar',
        'Chili garlic sauce',
      ].sort()
      expect(ingredients).toEqual(expectedIngredients)

      await RecipePage.editIngredient(0, { name: 'New ingredient' })

      const newIngredients = await RecipePage.getIngredients()

      const expectedNewIngredients = [
        'Napa cabbage, thinly sliced (about 4 cups)',
        'Small red cabbage, thinly sliced (about 2 cups)',
        'Medium carrots, grated (about 1 cup)',
        'Green onion, thinly sliced',
        'Minced cilantro',
        'Cooked, shredded chicken breast',
        'Slivered almonds, toasted',
        'Lime, juiced',
        'Natural peanut butter',
        'Low-sodium soy sauce',
        'New ingredient',
        'Fish sauce',
        'Rice vinegar',
        'Chili garlic sauce',
      ].sort()
      expect(newIngredients).toEqual(expectedNewIngredients)
    })

    it('displays the delete button and removes the ingredient', async () => {
      await RecipePage.goTo(3, 'Thai Chicken Salad')

      const expectedIngredients = [
        'Napa cabbage, thinly sliced (about 4 cups)',
        'Small red cabbage, thinly sliced (about 2 cups)',
        'Medium carrots, grated (about 1 cup)',
        'Green onion, thinly sliced',
        'Minced cilantro',
        'Cooked, shredded chicken breast',
        'Slivered almonds, toasted',
        'Lime, juiced',
        'Natural peanut butter',
        'Low-sodium soy sauce',
        'Agave nectar (or honey)',
        'Fish sauce',
        'Rice vinegar',
        'Chili garlic sauce',
      ].sort()
      expect(await RecipePage.getIngredients()).toEqual(expectedIngredients)

      await RecipePage.deleteIngredient(0)

      const expectedNewIngredients = [
        'Napa cabbage, thinly sliced (about 4 cups)',
        'Small red cabbage, thinly sliced (about 2 cups)',
        'Medium carrots, grated (about 1 cup)',
        'Green onion, thinly sliced',
        'Minced cilantro',
        'Cooked, shredded chicken breast',
        'Slivered almonds, toasted',
        'Lime, juiced',
        'Natural peanut butter',
        'Low-sodium soy sauce',
        'Fish sauce',
        'Rice vinegar',
        'Chili garlic sauce',
      ].sort()

      expect(await RecipePage.getIngredients()).toEqual(expectedNewIngredients)
    })
  })

  describe('on portion click', () => {
    it('displays the modify portion form and stores new value', async () => {
      await RecipePage.goTo(3, 'Thai Chicken Salad')

      expect(await RecipePage.getPortions()).toEqual('4')

      await RecipePage.editPortion('8')

      expect(await RecipePage.getPortions()).toEqual('8')
    })
  })

  describe('Notes', () => {
    it('can see and edit the recipe\'s notes', async () => {
      await RecipePage.goTo(2, 'Tartiflette')

      expect(await RecipePage.getNotes()).toBe('Please add more cheese if needed')

      await RecipePage.editNotes('Cover the plate with the cheese')

      expect(await RecipePage.getNotes()).toBe('Cover the plate with the cheese')
    })
  })

  describe('Step Note', () => {
    it('can see a step\'s notes', async () => {
      await RecipePage.goTo(3, 'Thai Chicken Salad')

      expect(await RecipePage.getStepNote()).toBe('Note: Careful not to break the almonds')
    })

    it('can edit a step\'s notes', async () => {
      await RecipePage.goTo(3, 'Thai Chicken Salad')

      await RecipePage.editStepNote('Careful not to break the almonds when slicing them')

      expect(await RecipePage.getStepNote()).toBe('Note: Careful not to break the almonds when slicing them')
    })
  })
})