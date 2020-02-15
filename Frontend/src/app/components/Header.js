import React, { useState } from 'react'

import './Header.css'
import PropTypes from 'prop-types'
import { useAppContext } from './StoreProvider'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import IconButton from '@material-ui/core/IconButton'
import Typography from '@material-ui/core/Typography'
import MenuIcon from '@material-ui/icons/Menu'
import DashboardIcon from '@material-ui/icons/Dashboard'
import CalendarTodayIcon from '@material-ui/icons/CalendarToday'
import KitchenIcon from '@material-ui/icons/Kitchen'
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart'
import MoreIcon from '@material-ui/icons/MoreVert'
import { LeftDrawer } from 'src/app/components/LeftDrawer'
import { makeStyles, useMediaQuery, useTheme } from '@material-ui/core'
import Menu from '@material-ui/core/Menu'
import Breadcrumbs from '@material-ui/core/Breadcrumbs'
import Link from '@material-ui/core/Link'
import { Link as RouterLink } from 'react-router-dom'
import SvgIcon from '@material-ui/core/SvgIcon'

export const DASHBOARD = 'Dashboard'
export const RECIPE = 'Recipes'
export const WEEK = 'Week'
export const CART = 'Carts'
export const LEARNING = 'Learning'

export const Header = () => {
  const { headerConfig } = useAppContext()
  const [isDrawerOpen, setDrawerOpen] = useState(false)
  const [anchorEl, setAnchorEl] = useState(null)
  const links = [
    {
      label: DASHBOARD,
      url: '/',
      icon: <DashboardIcon />,
    },
    {
      label: RECIPE,
      url: '/recipes',
      icon: <KitchenIcon />,
    },
    {
      label: WEEK,
      url: '/weeks',
      icon: <CalendarTodayIcon />,
    },
    {
      label: CART,
      url: '/carts',
      icon: <ShoppingCartIcon />,
    },
    {
      label: LEARNING,
      url: '/learning',
      icon: <SvgIcon viewBox='0 0 84.047 100'>
        <path d="m58.224 24.481c-2.201 0-3.994 1.787-3.994 4.006 0 2.215 1.793 4.001 3.994 4.001 2.208 0 3.994-1.786 3.994-4.001 0-2.219-1.786-4.006-3.994-4.006z" />
        <path d="m57.285 57.296c0 2.215 1.8 4.005 4.012 4.005 2.201 0 3.987-1.79 3.987-4.005 0-2.213-1.786-4.003-3.987-4.003-2.213 0-4.012 1.79-4.012 4.003z" />
        <path d="m28.045 20.137c-2.215 0-4.008 1.794-4.008 4.012 0 2.212 1.793 4.002 4.008 4.002 2.219 0 4.009-1.791 4.009-4.002 0-2.218-1.79-4.012-4.009-4.012z" />
        <path d="m46.116 0c-17.217 0-33.095 12.924-36.395 27.167-0.747 3.166-1.565 11.435-1.565 11.435l-7.928 19.962c-0.152 0.359-0.228 0.764-0.228 1.189 0 1.683 1.379 3.058 3.069 3.058h5.087v10.605c0 8.27 6.7 14.968 14.959 14.968h5.946v11.616h40.486v-32.206c8.83-6.946 14.5-17.736 14.5-29.842 0-20.963-16.987-37.952-37.931-37.952zm-28.641 46.167c-2.321 0-4.219-1.897-4.219-4.244 0-2.333 1.897-4.227 4.219-4.227 2.343 0 4.25 1.894 4.25 4.227 0 2.348-1.907 4.244-4.25 4.244zm55.995-5.733l-3.946 3.947-4.813-3.398c-0.927 0.484-1.901 0.868-2.917 1.127l-0.888 5.236h1.838l0.491 2.799c0.193 0.052 0.387 0.08 0.577 0.149 0.494 0.183 0.95 0.418 1.375 0.688l2.219-1.559 1.82 1.818-1.634 2.319c0.353 0.622 0.629 1.285 0.784 1.986l2.678 0.46v2.575l-2.788 0.48c-0.056 0.196-0.083 0.394-0.152 0.587-0.183 0.498-0.421 0.958-0.697 1.39l1.567 2.208-1.823 1.821-2.306-1.638c-0.625 0.353-1.285 0.629-1.99 0.784l-0.47 2.682h-2.574l-0.464-2.799c-0.2-0.056-0.4-0.083-0.598-0.156-0.494-0.183-0.953-0.418-1.382-0.69l-2.222 1.575-1.811-1.824 1.638-2.315c-0.353-0.626-0.629-1.285-0.784-1.99l-2.692-0.467v-2.564l2.813-0.48c0.055-0.197 0.083-0.395 0.155-0.591 0.18-0.491 0.414-0.947 0.684-1.376l-1.568-2.222 1.817-1.818 2.319 1.638c0.621-0.349 1.281-0.625 1.982-0.784l0.401-2.326h-4.848l-1.013-5.807c-0.218-0.069-0.436-0.125-0.653-0.208-0.781-0.287-1.507-0.646-2.201-1.05l-4.645 3.273-3.939-3.951 3.39-4.797c-0.487-0.929-0.871-1.907-1.133-2.927l-5.585-0.961v-4.586l-2.812 0.485c-0.045 0.135-0.056 0.273-0.107 0.404-0.184 0.498-0.422 0.954-0.681 1.396l2.156 3.055-2.596 2.588-3.165-2.232c-0.588 0.304-1.195 0.553-1.835 0.722l-0.64 3.663-3.645 0.003-0.664-3.822c-0.128-0.045-0.263-0.055-0.391-0.104-0.501-0.187-0.964-0.429-1.409-0.691l-3.065 2.16-2.585-2.592 2.242-3.176c-0.297-0.581-0.528-1.192-0.697-1.822l-3.688-0.632v-3.667l3.801-0.653c0.042-0.135 0.073-0.266 0.121-0.397 0.184-0.501 0.426-0.96 0.685-1.403l-2.16-3.055 2.582-2.589 3.179 2.236c0.588-0.305 1.199-0.557 1.842-0.727l0.639-3.666h3.646l0.665 3.824c0.132 0.042 0.267 0.052 0.394 0.1 0.502 0.187 0.961 0.43 1.407 0.688l3.055-2.153 2.595 2.589-2.246 3.172c0.305 0.584 0.553 1.192 0.719 1.832l3.663 0.629v2.665l4.797-0.823c0.069-0.218 0.124-0.435 0.207-0.653 0.287-0.785 0.65-1.521 1.062-2.219l-3.296-4.627 3.964-3.955 4.807 3.398c0.93-0.485 1.908-0.868 2.928-1.131l0.943-5.588h5.587l1.013 5.802c0.218 0.065 0.436 0.125 0.653 0.204 0.777 0.287 1.507 0.65 2.197 1.055l4.647-3.273 3.933 3.947-3.39 4.814c0.48 0.922 0.863 1.897 1.126 2.91l5.596 0.964v5.589l-5.806 0.999c-0.069 0.218-0.125 0.436-0.204 0.65-0.29 0.781-0.653 1.51-1.062 2.205l3.281 4.644z" />
      </SvgIcon>,
    },
  ]

  const breadcrumbLink = links.find(link => link.label === headerConfig.title)
    ? undefined
    : links.find(link => link.label === headerConfig.currentLink)

  return <HeaderDisplay
    title={headerConfig.title}
    links={links.map(link => ({ ...link, current: link.label === headerConfig.currentLink }))}
    isDrawerOpen={isDrawerOpen}
    handleDrawerToggle={() => setDrawerOpen(!isDrawerOpen)}
    closeDrawer={() => setDrawerOpen(false)}
    openDrawer={() => setDrawerOpen(true)}
    anchorEl={anchorEl}
    closeMenu={() => setAnchorEl(null)}
    toggleMenu={({ currentTarget }) => setAnchorEl(anchorEl === null ? currentTarget : null)}
    menus={headerConfig.menuList}
    breadcrumbLink={breadcrumbLink}
  />
}

export const DRAWER_WIDTH = 240
const useStyles = makeStyles(theme => ({
  drawerPaper: {
    width: DRAWER_WIDTH,
  },
  appBar: {
    [theme.breakpoints.up('md')]: {
      marginLeft: DRAWER_WIDTH,
      width: `calc(100% - ${DRAWER_WIDTH}px)`,
    },
  },
  menuIcon: {
    [theme.breakpoints.up('md')]: {
      display: 'none',
    },
  },
}))

const HeaderDisplay = ({
  title,
  links,
  isDrawerOpen,
  handleDrawerToggle,
  closeDrawer,
  openDrawer,
  anchorEl,
  closeMenu,
  toggleMenu,
  menus,
  breadcrumbLink,
}) => {
  const classes = useStyles()
  const theme = useTheme()
  const drawerVariant = useMediaQuery(theme.breakpoints.up('md')) ? 'permanent' : 'temporary'

  return (
    <nav>
      <AppBar position='fixed' className={classes.appBar}>
        <Toolbar>
          <IconButton
            color='inherit'
            aria-label='open drawer'
            edge='start'
            onClick={handleDrawerToggle}
            className={classes.menuIcon}
          >
            <MenuIcon />
          </IconButton>
          <Breadcrumbs aria-label="breadcrumb" style={{ flexGrow: 1, color: 'white' }}>
            {breadcrumbLink &&
            <Link style={{ color: 'inherit' }} component={RouterLink} to={breadcrumbLink.url}>{breadcrumbLink.label}</Link>
            }
            <Typography>{title}</Typography>
          </Breadcrumbs>
          <IconButton
            color='inherit'
            aria-label='open menu'
            edge='end'
            onClick={toggleMenu}
            style={{ display: menus.length === 0 ? 'none' : 'inherit' }}
            data-open-menu
          >
            <MoreIcon />
          </IconButton>
          <Menu
            keepMounted
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={closeMenu}
            onClick={toggleMenu}
          >
            {menus}
          </Menu>
        </Toolbar>
      </AppBar>
      <LeftDrawer
        open={isDrawerOpen}
        openDrawer={openDrawer}
        closeDrawer={closeDrawer}
        links={links}
        variant={drawerVariant}
        paperClassName={classes.drawerPaper}
      />
    </nav>
  )
}

HeaderDisplay.propTypes = {
  title: PropTypes.string,
  links: PropTypes.array,
  isDrawerOpen: PropTypes.bool,
  handleDrawerToggle: PropTypes.func,
  closeDrawer: PropTypes.func,
  openDrawer: PropTypes.func,
  anchorEl: PropTypes.object,
  closeMenu: PropTypes.func,
  toggleMenu: PropTypes.func,
  menus: PropTypes.array,
  breadcrumbLink: PropTypes.object,
}