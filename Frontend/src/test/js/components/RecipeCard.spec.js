import React from 'react';
import { shallow } from 'enzyme';

import RecipeCard from 'app-components/RecipeCard';

describe("RecipeCard", function () {
  it('renders without crashing', () => {
    const recipeCard = shallow(<RecipeCard />);
    expect(recipeCard).toBeDefined();
  });

  describe("Content", function() {
    let expectedTitle = "Recipe title";
    let expectedUrl = "/recipes/url";
    let expectedImgUrl = "http://example.com/image.png";

    beforeEach(() => {
      this.instance = shallow(<RecipeCard title={expectedTitle} imgUrl={expectedImgUrl} url={expectedUrl} />);
    });

    it('has a class card', () => {
      expect(this.instance.hasClass('card')).toBeTruthy();
    });

    it('is an anchor', () => {
      expect(this.instance.is('a')).toBeTruthy();
    });

    it('has an anchor with a url matching a given prop', () => {
      expect(this.instance.instance().props.url).toBe(expectedUrl);
      expect(this.instance.prop('href')).toBe(expectedUrl);
    });

    it('has a div with a class content', () => {
      expect(this.instance.find('div.content').length).toBe(1);
    });

    it('renders a title', () => {
      expect(this.instance.instance().props.title).toBe(expectedTitle);

      let contentDiv = this.instance.find('div.content');
      let headerDiv = contentDiv.find('div.header');
      expect(headerDiv.length).toBe(1);
      expect(headerDiv.text()).toBe(expectedTitle);
    });

    it('renders an image', () => {
      expect(this.instance.instance().props.imgUrl).toBe(expectedImgUrl);

      let imageDiv = this.instance.find('div.image');
      expect(imageDiv.length).toBe(1);

      let imageTag = imageDiv.find('img');
      expect(imageTag.length).toBe(1);
      expect(imageTag.prop('src')).toBe(expectedImgUrl);
    });
  });
});
