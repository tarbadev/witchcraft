import React from 'react';
import { shallow } from 'enzyme';

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

    it('is a div', () => {
      expect(this.instance.is('div')).toBeTruthy();
    });

    it('contains a Header', () => {
      expect(this.instance.find(Header).length).toBe(1);
    });

    it('contains a Content', () => {
      expect(this.instance.find(Content).length).toBe(1);
    });
  });
});
