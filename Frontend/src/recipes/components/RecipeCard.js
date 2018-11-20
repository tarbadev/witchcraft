import React from 'react'
import PropTypes from 'prop-types'
import Card from '@material-ui/core/Card'
import CardHeader from '@material-ui/core/CardHeader'
import CardMedia from '@material-ui/core/CardMedia'

import styles from 'src/recipes/components/RecipeCard.css'

export const RecipeCard = ({title, imgUrl}) => {
  let cardHeaderClasses = {
    root: styles.cardHeaderRoot,
    title: styles.cardHeaderTitle
  }

  return (
    <Card className={styles.recipeCard}>
      <CardMedia className={styles.cardMedia} title={title} image={imgUrl} />
      <CardHeader classes={cardHeaderClasses} title={title} />
    </Card>
  )
}


RecipeCard.propTypes = {
  title: PropTypes.string,
  imgUrl: PropTypes.string,
}
