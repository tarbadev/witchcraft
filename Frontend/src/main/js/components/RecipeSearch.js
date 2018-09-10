import React, {Component} from 'react';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';

import styles from 'app-components/RecipeSearch.css';

export default class RecipeSearch extends Component {
  render() {
    return (
      <Paper elevation={1} className={styles.paper}>
          <TextField
            fullWidth
            label="Search for a recipe"
            type="search"
            onChange={this.props.onSearch}
          />
      </Paper>
    )
  }
}
