import React from 'react'
import PropTypes from 'prop-types'
import Card from '@material-ui/core/Card'
import CardHeader from '@material-ui/core/CardHeader'
import CardMedia from '@material-ui/core/CardMedia'

import './RecipeCard.css'
import { onRecipeImageNotFoundError } from 'src/App'

export const RecipeCard = ({ title, imgUrl }) => {
  let cardHeaderClasses = {
    root: 'card-header-root',
    content: 'card-header-content',
    title: 'card-header-title witchcraft-title',
  }

  return (
    <Card className='recipe-card'>
      <CardMedia
        className='card-media'
        component='img'
        title={title}
        image={imgUrl}
        onError={onRecipeImageNotFoundError} />
      <CardHeader classes={cardHeaderClasses} title={title} />
    </Card>
  )
}

RecipeCard.propTypes = {
  title: PropTypes.string,
  imgUrl: PropTypes.string,
}
