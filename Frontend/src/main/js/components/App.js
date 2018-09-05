import React, { Component } from 'react';
import {
  Route,
  HashRouter, Switch
} from "react-router-dom";
import Grid from '@material-ui/core/Grid';

import Header from './Header'
import Content from './Content'

export default class App extends Component {
  render() {
    return (
      <Grid container spacing={24} justify="center">
        <Grid item xs={10}>
          <Header />
        </Grid>
        <Grid item xs={10}>
          <Content />
        </Grid>
      </Grid>
    );
  }
}
