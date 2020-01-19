import DialogContent from '@material-ui/core/DialogContent'
import MenuItem from '@material-ui/core/MenuItem'
import Dialog from '@material-ui/core/Dialog'
import React, { useState } from 'react'
import Paper from '@material-ui/core/Paper'
import Draggable from 'react-draggable'
import PropTypes from 'prop-types'
import convert from 'convert-units'
import { withStyles } from '@material-ui/core/styles'
import Typography from '@material-ui/core/Typography'
import IconButton from '@material-ui/core/IconButton'
import CloseIcon from '@material-ui/icons/Close'
import DialogTitle from '@material-ui/core/DialogTitle'
import { Grid } from '@material-ui/core'
import TextField from '@material-ui/core/TextField'

const PaperComponent = props => (
  <Draggable cancel={'[class*="MuiDialogContent-root"]'}>
    <Paper {...props} />
  </Draggable>
)

const styles = theme => ({
  root: {
    margin: 0,
    padding: theme.spacing(2),
    cursor: 'move',
  },
  closeButton: {
    position: 'absolute',
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500],
  },
})

const DialogTitleWithCloseButton = withStyles(styles)(({ children, classes, onClose, ...other }) => {
  return (
    <DialogTitle disableTypography className={classes.root} {...other}>
      <Typography variant="h6">{children}</Typography>
      {onClose ? (
        <IconButton aria-label="close" className={classes.closeButton} onClick={onClose} data-close-converter>
          <CloseIcon />
        </IconButton>
      ) : null}
    </DialogTitle>
  )
})

export const ConverterContainer = ({
  open,
  onClose,
}) => {
  const [measure, setMeasure] = useState('')
  const [leftUnit, setLeftUnit] = useState('')
  const [rightUnit, setRightUnit] = useState('')
  const [leftQuantity, setLeftQuantity] = useState('')
  const [rightQuantity, setRightQuantity] = useState('')

  const convertFromTo = (quantity, from, to) => {
    return Number(convert(quantity).from(from).to(to).toFixed(4)).toString()
  }

  const resetUnits = () => {
    setLeftQuantity('')
    setRightQuantity('')
    setLeftUnit('')
    setRightUnit('')
  }

  return (
    <Converter
      open={open}
      onClose={onClose}
      measures={convert().measures()}
      units={convert().list(measure).map(measure => measure.abbr)}
      measure={measure}
      onSelectMeasure={newMeasure => {
        setMeasure(newMeasure)
        resetUnits()
      }}
      leftUnit={leftUnit}
      onSelectLeftUnit={newLeftUnit => setLeftUnit(newLeftUnit)}
      rightUnit={rightUnit}
      onSelectRightUnit={newRightUnit => setRightUnit(newRightUnit)}
      leftQuantity={leftQuantity}
      rightQuantity={rightQuantity}
      onLeftQuantityChange={newLeftQuantity => {
        if (!Number.isNaN(newLeftQuantity)) {
          setLeftQuantity(`${newLeftQuantity}`)
          setRightQuantity(convertFromTo(newLeftQuantity, leftUnit, rightUnit))
        }
      }}
      onRightQuantityChange={newRightQuantity => {
        if (!Number.isNaN(newRightQuantity)) {
          setRightQuantity(`${newRightQuantity}`)
          setLeftQuantity(convertFromTo(newRightQuantity, rightUnit, leftUnit))
        }
      }}
    />
  )
}

ConverterContainer.propTypes = {
  open: PropTypes.bool,
  onClose: PropTypes.func,
}

const Converter = ({
  open,
  onClose,
  measures,
  units,
  onSelectMeasure,
  measure,
  leftUnit,
  onSelectLeftUnit,
  rightUnit,
  onSelectRightUnit,
  leftQuantity,
  rightQuantity,
  onLeftQuantityChange,
  onRightQuantityChange,
}) => {
  return (
    <Dialog
      aria-labelledby="customized-dialog-title"
      open={open}
      PaperComponent={PaperComponent}
      hideBackdrop
      disableBackdropClick
      disableEscapeKeyDown
      data-converter-container>
      <DialogTitleWithCloseButton id="customized-dialog-title" onClose={onClose}>
        Converter
      </DialogTitleWithCloseButton>
      <DialogContent dividers>
        <Grid container spacing={1}>
          <Grid item sm={12}>
            <TextField
              select
              label='Measure'
              value={measure}
              fullWidth
              data-select-measure
              onChange={({ target }) => onSelectMeasure(target.value)}
            >
              {measures.map(measure => <MenuItem key={measure} value={measure} data-menu-measure>{measure}</MenuItem>)}
            </TextField>
          </Grid>
          <Grid item sm={6} container direction='row'>
            <Grid item sm>
              <TextField
                label='Quantity'
                value={leftQuantity}
                fullWidth
                data-quantity-left
                onChange={({ target }) => onLeftQuantityChange(Number(target.value))}
              />
            </Grid>
            <Grid item sm={4} container alignItems='flex-end'>
              <TextField
                select
                label='Unit'
                fullWidth
                value={leftUnit}
                data-select-unit-left
                onChange={({ target }) => onSelectLeftUnit(target.value)}
              >
                {units.map(unit => <MenuItem key={`${unit}-left`} value={unit} data-menu-unit-left>{unit}</MenuItem>)}
              </TextField>
            </Grid>
          </Grid>
          <Grid item sm={6} container direction='row'>
            <Grid item sm>
              <TextField
                label='Quantity'
                fullWidth
                value={rightQuantity}
                data-quantity-right
                onChange={({ target }) => onRightQuantityChange(Number(target.value))}
              />
            </Grid>
            <Grid item sm={4} container alignItems='flex-end'>
              <TextField
                select
                label='Unit'
                fullWidth
                value={rightUnit}
                data-select-unit-right
                onChange={({ target }) => onSelectRightUnit(target.value)}
              >
                {units.map(unit => <MenuItem key={`${unit}-right`} value={unit} data-menu-unit-right>{unit}</MenuItem>)}
              </TextField>
            </Grid>
          </Grid>
        </Grid>
      </DialogContent>
    </Dialog>
  )
}

Converter.propTypes = {
  open: PropTypes.bool,
  onClose: PropTypes.func,
  onSelectMeasure: PropTypes.func,
  measures: PropTypes.array,
  units: PropTypes.array,
  measure: PropTypes.string,
  onSelectLeftUnit: PropTypes.func,
  leftUnit: PropTypes.string,
  onSelectRightUnit: PropTypes.func,
  rightUnit: PropTypes.string,
  leftQuantity: PropTypes.string,
  rightQuantity: PropTypes.string,
  onLeftQuantityChange: PropTypes.func,
  onRightQuantityChange: PropTypes.func,
}