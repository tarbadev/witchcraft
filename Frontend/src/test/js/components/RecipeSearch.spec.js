import React from 'react';
import { shallow } from 'enzyme';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';

import styles from 'app-components/RecipeSearch.css';
import RecipeSearch from 'app-components/RecipeSearch';

describe("RecipeSearch", function () {
  it('renders without crashing', () => {
    const recipeSearch = shallow(<RecipeSearch />);
    expect(recipeSearch).toBeDefined();
  });

  let lastTestedData = '';
  function testSearch(data) {
    lastTestedData = data.value;
  }

  describe("Content", function() {
    beforeEach(() => {
      this.instance = shallow(<RecipeSearch onSearch={testSearch} />);
      lastTestedData = '';
    });

    it('is a Paper with elevation set and class and a passed method', () => {
      expect(this.instance.instance().props.onSearch).toBe(testSearch);

      expect(this.instance.is(Paper)).toBeTruthy();
      expect(this.instance.props().elevation).toBe(1);
      expect(this.instance.props().className).toBe(styles.paper);
    });

    it('contains an TextField with a label and a type', () => {
      expect(this.instance.find(TextField).length).toBe(1);

      let textField = this.instance.find(TextField).at(0);
      expect(textField.props().label).toBe("Search for a recipe");
      expect(textField.props().type).toBe("search");
      expect(textField.props().fullWidth).toBeTruthy();
    });

    it('contains an TextField that calls a method onChange', () => {
      let textField = this.instance.find(TextField).at(0);
      expect(textField.props().onChange).toBe(testSearch);
      expect(lastTestedData).toBe('');

      let mockedResponse = { value: 'test' };
      textField.simulate('change', mockedResponse);

      expect(lastTestedData).toBe(mockedResponse.value);
    });
  });
});
