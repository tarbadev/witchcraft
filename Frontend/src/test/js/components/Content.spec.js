import React from 'react';
import { shallow } from 'enzyme';
import { Route } from 'react-router-dom'

import Content from 'app-components/Content';
import Home from 'app-components/Home'

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
      expect(this.instance.find(Route).length).toBe(1);
    });

    it('contains a Route to Home', () => {
      let route = this.instance.find(Route).at(0);
      expect(route.props().path).toBe('/');
      expect(route.props().component).toBe(Home);
    });
  });
});
