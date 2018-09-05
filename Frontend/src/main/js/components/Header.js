import React, { Component } from 'react';
import { Link } from "react-router-dom";
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';

export default class Header extends Component {
  render() {
    return (
      <Toolbar disableGutters={true}>
        <Link to="/"><Button>Home</Button></Link>
        <Link to="/recipes"><Button>Recipes</Button></Link>
        <Link to="/weeks"><Button>Weeks</Button></Link>
        <Link to="/carts"><Button>Carts</Button></Link>
      </Toolbar>
    );
  }
}
