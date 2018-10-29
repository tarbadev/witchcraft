import React from 'react'
import { shallow } from 'enzyme'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'

import {NewRecipe} from 'app-components/NewRecipe'

describe("NewRecipe", function () {
  it('calls the callback when input value changes', () => {
    const changeUrlFormInputSpy = jasmine.createSpy()
    const newRecipe = shallow(<NewRecipe changeUrlFormInput={changeUrlFormInputSpy} classes={{}} />)
    const url = "fakeUrl"

    newRecipe.find(TextField).simulate('change', {target: {value: url }})

    expect(changeUrlFormInputSpy).toHaveBeenCalledWith(url)
  })

  it('calls the callback when submit button is clicked', () => {
    const addRecipeFromUrlSpy = jasmine.createSpy()
    const url = 'fakeUrl'
    const newRecipe = shallow(<NewRecipe addRecipeFromUrl={addRecipeFromUrlSpy} classes={{}} url={url} />)

    newRecipe.find(Button).simulate('click', {})

    expect(addRecipeFromUrlSpy).toHaveBeenCalledWith({url: url})
  })

  it('redirects to /recipes when redirect set to true', () => {
    const setStateSpy = jasmine.createSpy()
    const pushSpy = jasmine.createSpy()

    shallow(<NewRecipe setState={setStateSpy} history={{ push: pushSpy }} redirect classes={{}} />)

    expect(pushSpy).toHaveBeenCalledWith('/recipes')
    expect(setStateSpy).toHaveBeenCalledWith("newRecipe.form", { recipeAdded: false, url: '' })
  })
})
