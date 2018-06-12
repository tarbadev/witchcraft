import React from 'react';
import ReactDOM from 'react-dom';
import ReactTestUtils from 'react-dom/test-utils';
import App from 'app-components/App';

describe("App", function () {
  it('renders without crashing', () => {
    let app = ReactTestUtils.renderIntoDocument(<App />);
    expect(app).toBeDefined();
  });
});
