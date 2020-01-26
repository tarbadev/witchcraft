import React from 'react'
import PropTypes from 'prop-types'
import SwipeableDrawer from '@material-ui/core/SwipeableDrawer'
import { Link } from 'react-router-dom'
import List from '@material-ui/core/List'
import ListItem from '@material-ui/core/ListItem'
import CardMedia from '@material-ui/core/CardMedia'
import ListItemText from '@material-ui/core/ListItemText'
import { ListItemIcon } from '@material-ui/core'

import logo from 'src/app/assets/logo.png'

export const LeftDrawer = ({ open, openDrawer, closeDrawer, links, variant, paperClassName }) => {
  return (
    <SwipeableDrawer
      variant={variant}
      open={open}
      onOpen={openDrawer}
      onClose={closeDrawer}
      classes={{
        paper: paperClassName,
      }}
      ModalProps={{
        keepMounted: true, // Better open performance on mobile.
      }}
    >
      <List>
        <ListItem divider>
          <CardMedia
            image={logo}
            src='logo.png'
            style={{ height: '100px', width: '80%', margin: 'auto', marginBottom: 10 }}
          />
        </ListItem>
        {links.map((link) => (
          <ListItem
            button
            key={link.label}
            component={Link}
            href={link.url}
            to={link.url}
            selected={link.current}
            onClick={closeDrawer}
          >
            <ListItemIcon>{link.icon}</ListItemIcon>
            <ListItemText primary={link.label} />
          </ListItem>
        ))}
      </List>
    </SwipeableDrawer>
  )
}

LeftDrawer.propTypes = {
  open: PropTypes.bool,
  openDrawer: PropTypes.func,
  closeDrawer: PropTypes.func,
  links: PropTypes.array,
  variant: PropTypes.string,
  paperClassName: PropTypes.string,
}