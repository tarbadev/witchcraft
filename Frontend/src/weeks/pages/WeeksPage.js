import React from 'react'
import { Redirect } from 'react-router-dom'

import { getCurrentWeek } from 'src/weeks/actions/WeekActions.js'

export const WeeksPage = () => {
  const getCurrentWeekUrl = () => {
    const { year, week } = getCurrentWeek()
    return `/weeks/${year}/${week}`
  }

  const weekUrl = getCurrentWeekUrl()

  return (
    <Redirect to={weekUrl} />
  )
}
