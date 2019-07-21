import React from 'react'
import { mount } from 'enzyme'

import { Step } from 'src/recipes/components/Step'

describe('Step', function () {
  it('displays a note when clicking on the note button', () => {
    const step = mount(<Step />)

    expect(step.find('[data-step-note]')).toHaveLength(0)
    step.find('[data-step-note-icon]').at(0).simulate('click')
    expect(step.find('[data-step-note]')).toHaveLength(3)
  })

  it('hides a note when clicking on the note button if it\'s already displayed', () => {
    const step = mount(<Step />)

    step.find('[data-step-note-icon]').at(0).simulate('click')
    expect(step.find('[data-step-note]')).toHaveLength(3)
    step.find('[data-step-note-icon]').at(0).simulate('click')
    expect(step.find('[data-step-note]')).toHaveLength(0)
  })

  it('hides a note when clicking on the note button if it\'s already displayed', () => {
    const note = 'Some note'
    const step = mount(<Step note={note} />)

    step.find('[data-step-note-icon]').at(0).simulate('click')
    expect(step.find('[data-step-note]').at(0).text()).toEqual(note)
  })
})
