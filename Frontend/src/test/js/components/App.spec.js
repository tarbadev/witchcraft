import React from 'react';
import { shallow } from 'enzyme';

import App from 'app-components/App';
import RecipeList from 'app-components/RecipeList';

describe("App", function () {
  it('renders without crashing', () => {
    const app = shallow(<App />);
    expect(app).toBeDefined();
  });

  describe("Content", function() {
    beforeEach(() => {
      this.instance = shallow(<App />);
    });

    it('contains a list of recipes', () => {
      expect(this.instance.find(RecipeList).length).toBe(1);
    });
  });
});
