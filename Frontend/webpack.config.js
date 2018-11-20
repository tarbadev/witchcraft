var path = require('path');
var webpack = require('webpack')
var SRC = path.resolve(__dirname, 'src');
var TEST_RESOURCES = path.resolve(__dirname, 'test_resources');
var DEST = path.resolve(__dirname, 'build/dist');

module.exports = {
  mode: 'development',
  devtool: 'source-map',
  entry: {
    app: SRC + '/index.js',
  },
  resolve: {
    extensions: ['.js', '.jsx'],
    alias: {
      'src': SRC,
      'test-resources': TEST_RESOURCES
    }
  },
  output: {
    path: DEST,
    filename: 'bundle.js',
    publicPath: '/dist/'
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
        query: {
          presets: ['@babel/preset-env', '@babel/preset-react'],
          plugins: [
            '@babel/plugin-proposal-object-rest-spread',
            '@babel/plugin-proposal-optional-chaining',
          ]
        },
        exclude: '/node_modules/',
        include: SRC
      },
      {
        test: /\.css$/,
        loader: [ 'style-loader', 'css-loader' ]
      }
    ]
  }
};
