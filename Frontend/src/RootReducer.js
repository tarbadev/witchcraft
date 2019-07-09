import { constant, updateIn } from 'updeep'

export const SET_STATE = 'SET_STATE'

export function setState(key, payload) {
  return { type: SET_STATE, key, payload }
}

const pages = {
  newRecipePage: {
    forms: {
      recipeAdded: false,
    },
    supportedDomains: [],
  },
  recipePage: {
    isDeleting: false,
    editableNotes: false,
    notes: '',
    notesInput: '',
  },
  newCartPage: {
    form: [],
  },
  weekPage: {
    modal: {
      isModalOpen: false,
      day: '',
      meal: '',
      displayExpressRecipeForm: false,
    },
    showSuccessMessage: false,
  },
  homePage: {
    favoriteRecipes: [],
    lastRecipes: [],
  },
}

const initialState = {
  pages,
  recipe: {
    id: 0,
    name: '',
    url: '',
    originUrl: '',
    imgUrl: '',
    ingredients: [],
    steps: [],
    favorite: false,
    portions: 0,
  },
  recipes: [],
  allRecipes: [],
  editRecipe: {
    form: {
      id: 0,
      name: '',
      url: '',
      imgUrl: '',
      ingredients: [
        {
          id: 0,
          name: '',
          quantity: 0,
          unit: '',
        },
      ],
      steps: [
        {
          id: 0,
          name: '',
        },
      ],
      favorite: false,
    },
  },
  expressRecipeForm: {
    recipeName: '',
  },
  carts: [],
  cart: {
    id: '',
    recipes: [],
    items: [],
    createdAt: '',
  },
  week: {
    days: [
      {
        lunch: {},
        diner: {},
      },
    ],
  },
}

export const reducer = (state = initialState, action) => {
  if (action.type === SET_STATE) {
    return updateIn(action.key, constant(action.payload), state)
  } else {
    return state
  }
}
