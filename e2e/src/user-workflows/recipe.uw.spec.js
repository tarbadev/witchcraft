import * as RecipePage from '../page-objects/recipe.po'
import * as RecipesPage from '../page-objects/recipes.po'
import * as EditRecipePage from '../page-objects/editRecipe.po'
import { waitForTextByCss } from '../page-objects/helpers.po'

describe('Recipe', () => {
  it('displays the ingredients', async () => {
    await RecipePage.goTo(3)
    await waitForTextByCss('.title', 'Thai Chicken Salad')

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
  })

  it('displays the steps', async () => {
    await RecipePage.goTo(3)
    await waitForTextByCss('.title', 'Thai Chicken Salad')

    const steps = await RecipePage.getSteps()
    const expectedSteps = [
      'In a large bowl, combine the Napa cabbage, red cabbage, carrot, green onion, cilantro and chicken breast. Toss with the dressing. Garnish with the toasted almonds. Serve.',
      'In a small glass bowl, combine the lime juice, peanut butter, soy sauce, agave nectar, fish sauce, rice vinegar and chili garlic sauce. Whisk until smooth.',
    ]
    expect(steps).toEqual(expectedSteps)
  })

  it('displays the number of portions', async () => {
    await RecipePage.goTo(3)
    await waitForTextByCss('.title', 'Thai Chicken Salad')

    expect(await RecipePage.getPortions()).toEqual('4')
  })

  describe('on heart button click', () => {
    it('adds the recipe as favourite', async () => {
      await RecipePage.goTo(3)
      await waitForTextByCss('.title', 'Thai Chicken Salad')

      expect(await RecipePage.isFavorite()).toBeFalsy()

      await RecipePage.clickOnFavoriteButton()
      await RecipePage.waitForFavoriteState()

      expect(await RecipePage.isFavorite()).toBeTruthy()
    })
  })

  describe('on delete button click', () => {
    it('delete the recipe and redirect to recipe list', async () => {
      await RecipePage.goTo(2)
      await waitForTextByCss('.title', 'Tartiflette')

      await RecipePage.clickOnDeleteButton()
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
      await RecipePage.goTo(3)
      await waitForTextByCss('.title', 'Thai Chicken Salad')

      await RecipePage.clickOnModifyButton()
      await EditRecipePage.waitForPageLoaded()

      expect(global.page.url()).toBe(`${appUrl}/recipes/3/edit`)
    })
  })

  describe('Notes', () => {
    it('can see the recipe\'s notes', async () => {
      await RecipePage.goTo(2)
      await waitForTextByCss('.title', 'Tartiflette')

      expect(await RecipePage.getNotes()).toBe('Please add more cheese if needed')

      await RecipePage.editNotes('Cover the plate with the cheese')

      expect(await RecipePage.getNotes()).toBe('Cover the plate with the cheese')
    })
  })
})