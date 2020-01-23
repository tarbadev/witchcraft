import React from 'react'
import convert from 'convert-units'
import { ConverterContainer } from './Converter'
import { createMount } from '@material-ui/core/test-utils'

describe('Converter', () => {
  let mount

  beforeEach(() => {
    mount = createMount()
  });

  afterEach(() => {
    mount.cleanUp()
  });

  it('displays the list of measures', () => {
    const measures = convert().measures()
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('[data-menu-measure]')).toHaveLength(0)

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')

    expect(converterContainer.find('li[data-menu-measure]')).toHaveLength(measures.length)
  })
  it('displays the selected measure', () => {
    const measure = convert().measures()[0]
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('div[data-select-measure] input').props().value).toEqual('')

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(0).simulate('click')

    expect(converterContainer.find('div[data-select-measure] input[type="hidden"]').props().value).toBe(measure)
  })

  it('displays the list of units after the measures are selected', () => {
    const measure = convert().measures()[0]
    const units = convert().list(measure)
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('li[data-menu-unit-left]')).toHaveLength(0)

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(0).simulate('click')

    converterContainer.find('div[data-select-unit-left] div div').at(0).simulate('mousedown')
    expect(converterContainer.find('li[data-menu-unit-left]')).toHaveLength(units.length)
  })

  it('displays the selected left unit', () => {
    const measure = convert().measures()[0]
    const unit = convert().list(measure)[0].abbr
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('div[data-select-unit-left] input').props().value).toEqual('')

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(0).simulate('click')

    converterContainer.find('div[data-select-unit-left] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-left]').at(0).simulate('click')

    expect(converterContainer.find('div[data-select-unit-left] input').props().value).toBe(unit)
  })

  it('displays the selected right unit', () => {
    const measure = convert().measures()[0]
    const unit = convert().list(measure)[2].abbr
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('div[data-select-unit-right] input').props().value).toEqual('')

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(0).simulate('click')

    converterContainer.find('div[data-select-unit-right] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-right]').at(2).simulate('click')

    expect(converterContainer.find('div[data-select-unit-right] input').props().value).toBe(unit)
  })

  it('when 2 units are selected and left unit quantity is filled displays result in right unit quantity', () => {
    const leftQuantity = '50'
    const rightQuantity = '1.7637'
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('div[data-select-unit-right] input').props().value).toEqual('')

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(2).simulate('click')

    converterContainer.find('div[data-select-unit-left] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-left]').at(2).simulate('click')
    converterContainer.find('div[data-select-unit-right] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-right]').at(5).simulate('click')

    converterContainer.find('div[data-quantity-left] input').simulate('change', { target: { value: leftQuantity } })

    expect(converterContainer.find('div[data-quantity-right] input').props().value).toBe(rightQuantity)
  })

  it('does not display other characters than numbers for quantity fields', () => {
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('div[data-select-unit-right] input').props().value).toEqual('')

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(2).simulate('click')

    converterContainer.find('div[data-select-unit-left] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-left]').at(2).simulate('click')
    converterContainer.find('div[data-select-unit-right] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-right]').at(5).simulate('click')

    converterContainer.find('div[data-quantity-left] input').simulate('change', { target: { value: 'zsdffds;' } })
    expect(converterContainer.find('div[data-quantity-left] input').props().value).toBe('')
    expect(converterContainer.find('div[data-quantity-right] input').props().value).toBe('')

    converterContainer.find('div[data-quantity-right] input').simulate('change', { target: { value: '!@#$%^&*;' } })
    expect(converterContainer.find('div[data-quantity-left] input').props().value).toBe('')
    expect(converterContainer.find('div[data-quantity-right] input').props().value).toBe('')
  })

  it('when 2 units are selected and right unit quantity is filled displays result in left unit quantity', () => {
    const leftQuantity = '50'
    const rightQuantity = '1.7637'
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('div[data-select-unit-left] input').props().value).toEqual('')

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(2).simulate('click')

    converterContainer.find('div[data-select-unit-left] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-left]').at(2).simulate('click')
    converterContainer.find('div[data-select-unit-right] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-right]').at(5).simulate('click')

    converterContainer.find('div[data-quantity-right] input').simulate('change', { target: { value: rightQuantity } })

    expect(converterContainer.find('div[data-quantity-left] input').props().value).toBe(leftQuantity)
  })

  it('when measure changes, clears the units and quantities', () => {
    const leftQuantity = '1.6127'
    const rightQuantity = '1.7637'
    const converterContainer = mount(<ConverterContainer open={true} />)

    expect(converterContainer.find('div[data-select-unit-right] input').props().value).toEqual('')
    expect(converterContainer.find('div[data-select-unit-left] input').props().value).toEqual('')
    expect(converterContainer.find('div[data-quantity-left] input').props().value).toEqual('')
    expect(converterContainer.find('div[data-quantity-right] input').props().value).toEqual('')

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(2).simulate('mousedown')

    converterContainer.find('div[data-select-unit-left] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-left]').at(2).simulate('click')
    converterContainer.find('div[data-select-unit-right] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-unit-right]').at(5).simulate('click')

    converterContainer.find('div[data-quantity-right] input').simulate('change', { target: { value: rightQuantity } })

    expect(converterContainer.find('div[data-quantity-left] input').props().value).toBe(leftQuantity)

    converterContainer.find('div[data-select-measure] div div').at(0).simulate('mousedown')
    converterContainer.find('li[data-menu-measure]').at(0).simulate('click')

    expect(converterContainer.find('div[data-select-unit-right] input').props().value).toEqual('')
    expect(converterContainer.find('div[data-select-unit-left] input').props().value).toEqual('')
    expect(converterContainer.find('div[data-quantity-left] input').props().value).toEqual('')
    expect(converterContainer.find('div[data-quantity-right] input').props().value).toEqual('')
  })
})