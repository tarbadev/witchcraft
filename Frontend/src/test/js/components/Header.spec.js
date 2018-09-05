import React from 'react';
import { shallow } from 'enzyme';
import { Link } from "react-router-dom";

import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';

import Header from 'app-components/Header';

describe("Header", function () {
  it('renders without crashing', () => {
    const header = shallow(<Header />);
    expect(header).toBeDefined();
  });

  describe("Content", function() {
    beforeEach(() => {
      this.instance = shallow(<Header />);
    });

    it('is a Toolbar', () => {
      expect(this.instance.is(Toolbar)).toBeTruthy();
      expect(this.instance.props().disableGutters).toBeTruthy();
    });

    it('contains a 4 Link', () => {
      expect(this.instance.find(Link).length).toBe(4);
    });

    it('contains a home Button', () => {
      let link = this.instance.find(Link).at(0);

      expect(link.props().to).toBe("/");
      expect(link.find(Button).at(0).children().text()).toBe("Home");
    });

    it('contains a recipes Button', () => {
      let link = this.instance.find(Link).at(1);

      expect(link.props().to).toBe("/recipes");
      expect(link.find(Button).at(0).children().text()).toBe("Recipes");
    });

    it('contains a weeks Button', () => {
      let link = this.instance.find(Link).at(2);

      expect(link.props().to).toBe("/weeks");
      expect(link.find(Button).at(0).children().text()).toBe("Weeks");
    });

    it('contains a cart Button', () => {
      let link = this.instance.find(Link).at(3);

      expect(link.props().to).toBe("/carts");
      expect(link.find(Button).at(0).children().text()).toBe("Carts");
    });
  });
});
