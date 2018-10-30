import {updateIn, constant} from 'updeep'

export const SET_STATE = 'SET_STATE'

export function setState(key, payload) {
	return {
		type: SET_STATE,
		key,
		payload
	}
}

const initialState = {
	newRecipe: {
		forms: {
			autoUrl: {
				url: ''
			},
			manualUrl: {
				name: '',
				url: '',
				imageUrl: '',
				ingredients: '',
				steps: '',
			}
		}
	}
}

export const reducer = (state = initialState, action) => {
	switch (action.type) {
		case SET_STATE:
			return updateIn(action.key, constant(action.payload), state)
		default:
			return state
	}
}
