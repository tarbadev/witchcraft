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
      expect(this.instance.find(Toolbar).length).toBe(1);
    });

    it('contains a 4 Button', () => {
      expect(this.instance.find(Button).length).toBe(4);
    });

    it('contains a home Link', () => {
      let button = this.instance.find(Button).at(0);

      expect(button.find(Link).at(0).children().text()).toBe("Home");
      expect(button.find(Link).at(0).props().to).toBe("/");
    });

    it('contains a recipes Link', () => {
      let button = this.instance.find(Button).at(1);

      expect(button.find(Link).at(0).children().text()).toBe("Recipes");
      expect(button.find(Link).at(0).props().to).toBe("/recipes");
    });

    it('contains a weeks Link', () => {
      let button = this.instance.find(Button).at(2);

      expect(button.find(Link).at(0).children().text()).toBe("Weeks");
      expect(button.find(Link).at(0).props().to).toBe("/weeks");
    });

    it('contains a cart Link', () => {
      let button = this.instance.find(Button).at(3);

      expect(button.find(Link).at(0).children().text()).toBe("Carts");
      expect(button.find(Link).at(0).props().to).toBe("/carts");
    });
  });
});
