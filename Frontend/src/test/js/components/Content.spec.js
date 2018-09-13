import React from 'react';
import { shallow } from 'enzyme';
import { Route } from 'react-router-dom'

import Content from 'app-components/Content';
import Home from 'app-components/Home'
import Recipes from 'app-components/Recipes'
import Recipe from 'app-components/Recipe'

describe("Content", function () {
  it('renders without crashing', () => {
    const content = shallow(<Content />);
    expect(content).toBeDefined();
  });

  describe("Content", function() {
    beforeEach(() => {
      this.instance = shallow(<Content />);
    });

    it('contains the Route list', () => {
      expect(this.instance.find(Route).length).toBe(3);
    });

    it('contains a Route to Home', () => {
      let route = this.instance.find(Route).at(0);
      expect(route.props().path).toBe('/');
      expect(route.props().exact).toBeTruthy();
      expect(route.props().component).toBe(Home);
    });

    it('contains a Route to Recipes', () => {
      let route = this.instance.find(Route).at(1);
      expect(route.props().path).toBe('/recipes');
      expect(route.props().exact).toBeTruthy();
      expect(route.props().component).toBe(Recipes);
    });

    it('contains a Route to Recipe', () => {
      let route = this.instance.find(Route).at(2);
      expect(route.props().path).toBe('/recipes/:id');
      expect(route.props().component).toBe(Recipe);
    });
  });
});