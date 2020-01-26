import React from 'react'
import { mount } from 'enzyme'

import { Step } from 'src/recipes/components/Step'
import { mockAppContext } from '../../testUtils'

describe('Step', function () {
  it('dispatches a saveStepNote when save note button is clicked', () => {
    mockAppContext()
    const onSaveNoteSpy = jest.fn()
    const newNote = 'Some new note'
    const step = mount(<Step note='Some note' onSaveNote={onSaveNoteSpy} />)

    step.find('[data-display-value]').at(0).simulate('click')
    step.find('[data-edit-value] textarea').at(0).simulate('change', { target: { value: newNote } })
    step.find('.notes-container__update-value-button').at(0).simulate('mousedown')

    expect(onSaveNoteSpy).toHaveBeenLastCalledWith(newNote)
  })
})
