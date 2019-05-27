import * as RecipePage from '../page-objects/recipe.po'
import * as EditRecipePage from '../page-objects/editRecipe.po'

describe('Edit Recipe', () => {
  describe('On submit', () => {
    it('saves the recipe and redirects to recipe page', async () => {
      await EditRecipePage.goTo(3)

      await EditRecipePage.fillInForm({
        name: 'Thai Chicken Salad Recipe',
        url: 'https://www.cookincanuck.com/thai-chicken-salad-recipe/',
        imgUrl: 'https://www.cookincanuck.com/wp-content/uploads/2017/09/Thai-Chicken-Salad-Recipe-Cookin-Canuck-3.jpg',
        ingredients: [
          {
            name: 'napa cabbage, thinly sliced (about 4 cups)',
            quantity: 0.5,
            unit: '',
          },
          {
            name: 'small red cabbage, thinly sliced (about 2 cups)',
            quantity: 0.25,
            unit: '',
          },
          {
            name: 'medium carrots, grated (about 1 cup)',
            quantity: 2,
            unit: '',
          },
          {
            name: 'green onion, thinly sliced',
            quantity: 3,
            unit: '',
          },
          {
            name: 'minced cilantro',
            quantity: 0.25,
            unit: 'cup',
          },
          {
            name: 'cooked, shredded chicken breast',
            quantity: 2,
            unit: 'cup',
          },
          {
            name: 'slivered almonds, toasted',
            quantity: 3,
            unit: 'tbsp',
          },
          {
            name: 'lime juice',
            quantity: 1,
            unit: '',
          },
          {
            name: 'peanut butter',
            quantity: 3,
            unit: 'tbsp',
          },
          {
            name: 'soy sauce',
            quantity: 2,
            unit: 'tbsp',
          },
          {
            name: 'agave nectar',
            quantity: 3,
            unit: 'tsp',
          },
          {
            name: 'fish sauce',
            quantity: 2,
            unit: 'tsp',
          },
          {
            name: 'rice vinegar',
            quantity: 2,
            unit: 'tsp',
          },
          {
            name: 'chili garlic sauce',
            quantity: 1,
            unit: 'tsp',
          },
        ],
        steps: [
          {
            name: 'In a large bowl, combine the Napa cabbage, red cabbage, carrot, green onion, cilantro and chicken breast. Toss with the dressing. Garnish with the toasted almonds.',
          },
          {
            name: 'In a small glass bowl, combine the lime juice, peanut butter, soy sauce, agave nectar, fish sauce, rice vinegar and chili garlic sauce. Whisk until smooth.',
          },
        ],
      })

      await EditRecipePage.submitEditedRecipe()
      await RecipePage.waitForPageLoaded()

      const ingredients = await RecipePage.getIngredients()
      const expectedIngredients = [
        'napa cabbage, thinly sliced (about 4 cups)',
        'small red cabbage, thinly sliced (about 2 cups)',
        'medium carrots, grated (about 1 cup)',
        'green onion, thinly sliced',
        'minced cilantro',
        'cooked, shredded chicken breast',
        'slivered almonds, toasted',
        'lime juice',
        'peanut butter',
        'soy sauce',
        'agave nectar',
        'fish sauce',
        'rice vinegar',
        'chili garlic sauce',
      ].sort()
      expect(ingredients).toEqual(expectedIngredients)

      const steps = await RecipePage.getSteps()
      const expectedSteps = [
        'In a large bowl, combine the Napa cabbage, red cabbage, carrot, green onion, cilantro and chicken breast. Toss with the dressing. Garnish with the toasted almonds.',
        'In a small glass bowl, combine the lime juice, peanut butter, soy sauce, agave nectar, fish sauce, rice vinegar and chili garlic sauce. Whisk until smooth.',
      ]
      expect(steps).toEqual(expectedSteps)
    }, 45000)
  })
})