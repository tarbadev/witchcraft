import React from 'react'
import PropTypes from 'prop-types'
import Card from '@material-ui/core/Card'
import CardHeader from '@material-ui/core/CardHeader'
import CardMedia from '@material-ui/core/CardMedia'

import 'src/recipes/components/RecipeCard.css'

export const RecipeCard = ({ title, imgUrl }) => {
  let cardHeaderClasses = {
    root: 'card-header-root',
    title: 'card-header-title',
  }

  return (
    <Card className={'recipe-card'}>
      <CardMedia className={'card-media'} title={title} image={imgUrl} />
      <CardHeader classes={cardHeaderClasses} title={title} />
    </Card>
  )
}

RecipeCard.propTypes = {
  title: PropTypes.string,
  imgUrl: PropTypes.string,
}
