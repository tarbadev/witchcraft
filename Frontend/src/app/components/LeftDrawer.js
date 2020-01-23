import React from 'react'
import PropTypes from 'prop-types'
import Drawer from '@material-ui/core/Drawer'
import { Link } from 'react-router-dom'
import List from '@material-ui/core/List'
import ListItem from '@material-ui/core/ListItem'
import CardMedia from '@material-ui/core/CardMedia'
import ListItemText from '@material-ui/core/ListItemText'
import { ListItemIcon } from '@material-ui/core'

export const LeftDrawer = ({ open, closeDrawer, links, variant, paperClassName }) => {
  return (
    <Drawer
      variant={variant}
      open={open}
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
            image='logo.png'
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
    </Drawer>
  )
}

LeftDrawer.propTypes = {
  open: PropTypes.bool,
  closeDrawer: PropTypes.func,
  links: PropTypes.array,
  variant: PropTypes.string,
  paperClassName: PropTypes.object,
}