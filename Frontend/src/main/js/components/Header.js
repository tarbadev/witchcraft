import React, { Component } from 'react';
import { Link } from "react-router-dom";
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';

export default class Header extends Component {
  render() {
    return (
      <Toolbar disableGutters={true}>
        <Button component={Link} to="/home">Home</Button>
        <Button component={Link} to="/recipes">Recipes</Button>
        <Button component={Link} to="/weeks">Weeks</Button>
        <Button component={Link} to="/carts">Carts</Button>
      </Toolbar>
    );
  }
}
