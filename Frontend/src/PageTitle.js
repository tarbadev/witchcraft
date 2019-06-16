import React from 'react'
import Helmet from 'react-helmet'
import PropTypes from 'prop-types'

export const PageTitle = ({ title }) => {
  const baseTitle = 'Witchcraft'
  const fullTitle = (title ? title + ' - ' : '') + baseTitle
  return (
    <Helmet>
      <title>{fullTitle}</title>
    </Helmet>
  )
}

PageTitle.propTypes = {
  title: PropTypes.string,
}

export const withTitle = ({ component: Component, title }) => () => (
  <div>
    <PageTitle title={title} />
    <Component />
  </div>
)