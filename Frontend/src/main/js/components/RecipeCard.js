import React, { Component } from 'react';

export default class RecipeCard extends Component {
  render() {
    return (
      <a className='card'>
        <div className='image'>
          <img src={this.props.imgUrl} />
        </div>
        <div className='content'>
          <div className='header'>
            {this.props.title}
          </div>
        </div>
      </a>
    );
  }
}
