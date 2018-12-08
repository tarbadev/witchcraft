const webpack = require('webpack')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const CleanWebpackPlugin = require('clean-webpack-plugin')

const SRC = path.resolve(__dirname, 'src')
const PUBLIC = path.resolve(__dirname, 'public')
const TEST_RESOURCES = path.resolve(__dirname, 'test_resources')
const DEST = path.resolve(__dirname, 'build/dist')

module.exports = env => {
    const envKeys = env
        ? Object.keys(env).reduce((prev, next) => {
            prev[`process.env.${next}`] = JSON.stringify(env[next]);
            return prev;
          }, {})
        : {};

    return {
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
        publicPath: ''
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
      },
      devServer: {
        compress: false,
        port: 5000,
        host: 'localhost',
        historyApiFallback: true
      },
      plugins: [
        new CleanWebpackPlugin([DEST]),
        new HtmlWebpackPlugin({
          title: 'Witchcraft',
          favicon: PUBLIC + '/logo.png',
          templateContent: '<div id="react"></div>',
        }),
        new webpack.DefinePlugin(envKeys),
        new CopyWebpackPlugin([
            { from: 'package.json', to: DEST },
            { from: PUBLIC + '/index.js', to: DEST }
          ])
      ]
    }
}
