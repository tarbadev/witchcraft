import React from 'react'
import { shallow } from 'enzyme'
import Card from '@material-ui/core/Card'
import CardHeader from '@material-ui/core/CardHeader'
import CardMedia from '@material-ui/core/CardMedia'

import styles from 'src/recipes/components/RecipeCard.css'
import {RecipeCard} from 'src/recipes/components/RecipeCard'

describe('RecipeCard', function () {
  it('renders without crashing', () => {
    const recipeCard = shallow(<RecipeCard />)
    expect(recipeCard).toBeDefined()
  })

  describe('Content', function() {
    let expectedTitle = 'Recipe title'
    let expectedUrl = '/recipes/url'
    let expectedImgUrl = 'http://example.com/image.png'

    beforeEach(() => {
      this.instance = shallow(<RecipeCard title={expectedTitle} imgUrl={expectedImgUrl} url={expectedUrl} />)
    })

    it('is a Card', () => {
      expect(this.instance.is(Card)).toBeTruthy()
    })

    it('contains a CardHeader with a title from props and a classes prop', () => {
      expect(this.instance.find(CardHeader).length).toBe(1)

      let cardHeader = this.instance.find(CardHeader).at(0)
      expect(cardHeader.props().title).toBe(expectedTitle)

      expect(cardHeader.props().classes.root).toBe(styles.cardHeaderRoot)
      expect(cardHeader.props().classes.title).toBe(styles.cardHeaderTitle)
    })

    it('contains a CardMedia with a image url and title from props and a className', () => {
      expect(this.instance.find(CardMedia).length).toBe(1)

      let cardMedia = this.instance.find(CardMedia).at(0)
      expect(cardMedia.props().image).toBe(expectedImgUrl)
      expect(cardMedia.props().title).toBe(expectedTitle)

      expect(cardMedia.props().className).toBe(styles.cardMedia)
    })
  })
})
