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
      'app-root': path.resolve(__dirname, 'src/main/js/'),
      'app-components': path.resolve(__dirname, 'src/main/js/components/'),
      'app-services': path.resolve(__dirname, 'src/main/js/services/'),
      'app-actions': path.resolve(__dirname, 'src/main/js/actions/'),
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
          presets: ['es2015', 'react'],
          plugins: ['transform-class-properties', 'babel-plugin-transform-object-rest-spread']
        },
        exclude: '/node_modules/',
        include: ROOT
      },
      {
        test: /\.css$/,
        loader: [ 'style-loader', 'css-loader' ]
      }
    ]
  }
};
