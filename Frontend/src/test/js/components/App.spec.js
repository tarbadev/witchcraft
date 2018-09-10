import React from 'react';
import { shallow } from 'enzyme';
import Grid from '@material-ui/core/Grid';

import styles from 'app-components/App.css';
import App from 'app-components/App';
import Header from 'app-components/Header'
import Content from 'app-components/Content'

describe("App", function () {
  it('renders without crashing', () => {
    const app = shallow(<App />);
    expect(app).toBeDefined();
  });

  describe("Content", function() {
    beforeEach(() => {
      this.instance = shallow(<App />);
    });

    it('is a Grid container with spacing and justify props with className', () => {
      expect(this.instance.is(Grid)).toBeTruthy();
      expect(this.instance.props().container).toBeTruthy();
      expect(this.instance.props().spacing).toBe(24);
      expect(this.instance.props().justify).toBe("center");
    });

    it('contains a Header in a Grid item', () => {
      let item = this.instance.findWhere(node => node.props().item).at(0);
      expect(item.props().xs).toBe(10);
      expect(item.props().className).toBe(styles.header);
      expect(item.find(Header).length).toBe(1);
    });

    it('contains a Content in a Grid item', () => {
      let item = this.instance.findWhere(node => node.props().item).at(1);
      expect(item.props().xs).toBe(10);
      expect(item.find(Content).length).toBe(1);
    });
  });
});
