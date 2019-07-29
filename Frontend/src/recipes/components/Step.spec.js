import React from 'react'
import { mount } from 'enzyme'

import { Step } from 'src/recipes/components/Step'
import { mockAppContext } from '../../testUtils'

describe('Step', function () {
  it('displays a note when clicking on the note button', () => {
    const step = mount(<Step />)

    expect(step.find('.notes-container__empty-value')).toHaveLength(0)
    step.find('[data-step-note-icon]').at(0).simulate('click')
    expect(step.find('.notes-container__empty-value')).toHaveLength(3)
  })

  it('hides a note when clicking on the note button if it\'s already displayed', () => {
    const step = mount(<Step />)

    step.find('[data-step-note-icon]').at(0).simulate('click')
    expect(step.find('.notes-container__empty-value')).toHaveLength(3)
    step.find('[data-step-note-icon]').at(0).simulate('click')
    expect(step.find('.notes-container__empty-value')).toHaveLength(0)
  })

  it('hides a note when clicking on the note button if it\'s already displayed', () => {
    mockAppContext()
    const note = 'Some note'
    const step = mount(<Step note={note} />)

    step.find('[data-step-note-icon]').at(0).simulate('click')
    expect(step.find('[data-display-value]').at(0).text()).toEqual(note)
  })

  it('dispatches a saveStepNote when save note button is clicked', () => {
    mockAppContext()
    const onSaveNoteSpy = jest.fn()
    const newNote = 'Some new note'
    const step = mount(<Step note='Some note' onSaveNote={onSaveNoteSpy} />)

    step.find('[data-step-note-icon]').at(0).simulate('click')
    step.find('[data-display-value]').at(0).simulate('click')
    step.find('[data-edit-value] textarea').at(0).simulate('change', { target: { value: newNote } })
    step.find('.notes-container__update-value-button').at(0).simulate('mousedown')

    expect(onSaveNoteSpy).toHaveBeenLastCalledWith(newNote)
  })
})
