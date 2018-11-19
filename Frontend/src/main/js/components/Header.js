import React from 'react'
import { Link } from 'react-router-dom'
import Toolbar from '@material-ui/core/Toolbar'
import Button from '@material-ui/core/Button'

export const Header = () => {
  return (
    <Toolbar disableGutters={true}>
      <Button component={Link} to='/'>Home</Button>
      <Button component={Link} to='/recipes'>Recipes</Button>
      <Button component={Link} to='/weeks'>Weeks</Button>
      <Button component={Link} to='/carts'>Carts</Button>
    </Toolbar>
  )
}
