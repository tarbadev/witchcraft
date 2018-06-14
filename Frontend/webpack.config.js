var path = require('path');
var webpack = require('webpack')
var ROOT = path.resolve(__dirname, 'src');
var SRC = path.resolve(ROOT, 'main/js');
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
      'app-components': path.resolve(__dirname, 'src/main/js/components/'),
      'app-services': path.resolve(__dirname, 'src/main/js/services/'),
      'test-resources': path.resolve(__dirname, 'src/test/resources/')
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
          presets: ['react', 'es2015']
          // plugins: ['transform-decorators-legacy']
        },
        exclude: '/node_modules/',
        include: ROOT
      }

//      {test: /\.css$/, loader: 'style-loader!css-loader'}
    ]
  }
};
