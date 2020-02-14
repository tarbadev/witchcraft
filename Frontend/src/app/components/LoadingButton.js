import PropTypes from 'prop-types'
import CircularProgress from '@material-ui/core/CircularProgress'
import { Button, makeStyles } from '@material-ui/core'
import React from 'react'

const useStyles = makeStyles({
  button: {
    position: 'relative',
  },
  buttonProgress: {
    position: 'absolute',
    top: '50%',
    left: '50%',
    marginTop: -12,
    marginLeft: -12,
  },
})

export const LoadingButton = ({ isLoading, onClick, buttonDataTag, progressDataTag, children }) => {
  const classes = useStyles()

  return (
    <Button
      variant='contained'
      data-tag={buttonDataTag}
      color='primary'
      href=''
      onClick={onClick}
      disabled={isLoading}
      className={classes.button}
    >
      {children}
      {isLoading &&
      <CircularProgress data-tag={progressDataTag} size={24} className={classes.buttonProgress} />}
    </Button>
  )
}

LoadingButton.propTypes = {
  isLoading: PropTypes.bool,
  onClick: PropTypes.func,
  buttonDataTag: PropTypes.string,
  progressDataTag: PropTypes.string,
  children: PropTypes.object,
}