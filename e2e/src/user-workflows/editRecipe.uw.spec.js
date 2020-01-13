import * as RecipePage from '../page-objects/recipe.po'
import * as EditRecipePage from '../page-objects/editRecipe.po'
import { waitForTextByCss } from '../page-objects/helpers.po'

describe('Edit Recipe', () => {
  describe('On submit', () => {
    it('saves the recipe and redirects to recipe page', async () => {
      await EditRecipePage.goTo(3)

      await EditRecipePage.fillInForm({
        name: 'Thai Chicken Salad Recipe',
        url: 'https://www.cookincanuck.com/thai-chicken-salad-recipe/',
        imgUrl: 'https://www.cookincanuck.com/wp-content/uploads/2017/09/Thai-Chicken-Salad-Recipe-Cookin-Canuck-3.jpg',
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
      await waitForTextByCss('.title', 'Thai Chicken Salad Recipe')

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

      const steps = await RecipePage.getSteps()
      const expectedSteps = [
        'In a large bowl, combine the Napa cabbage, red cabbage, carrot, green onion, cilantro and chicken breast. Toss with the dressing. Garnish with the toasted almonds.',
        'In a small glass bowl, combine the lime juice, peanut butter, soy sauce, agave nectar, fish sauce, rice vinegar and chili garlic sauce. Whisk until smooth.',
      ]
      expect(steps).toEqual(expectedSteps)
    }, 10000)
  })
})