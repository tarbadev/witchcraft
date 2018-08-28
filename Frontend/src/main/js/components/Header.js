import React, { Component } from 'react';
import { Link } from "react-router-dom";
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';

export default class Header extends Component {
  render() {
    return (
      <Toolbar>
        <Button><Link to="/">Home</Link></Button>
        <Button><Link to="/recipes">Recipes</Link></Button>
        <Button><Link to="/weeks">Weeks</Link></Button>
        <Button><Link to="/carts">Carts</Link></Button>
      </Toolbar>
    );
  }
}
