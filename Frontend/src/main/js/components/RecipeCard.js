import React, { Component } from 'react';

export default class RecipeCard extends Component {
  render() {
    return (
      <a className='card'>
        <div className='content'>
          <div className='header'>
            {this.props.title}
          </div>
        </div>
      </a>
    );
  }
}
