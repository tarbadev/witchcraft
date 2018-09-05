import React, { Component } from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';

import styles from 'app-components/RecipeCard.css';

export default class RecipeCard extends Component {
  render() {
    let cardHeaderClasses = {
      root: styles.cardHeaderRoot,
      title: styles.cardHeaderTitle
    };

    return (
      <Card className={styles.recipeCard}>
        <CardMedia className={styles.cardMedia} title={this.props.title} image={this.props.imgUrl} />
        <CardHeader classes={cardHeaderClasses} title={this.props.title} />
      </Card>
    );
  }
}
