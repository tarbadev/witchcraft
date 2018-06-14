var webpackConfig = require('./webpack.config.js');

module.exports = function(config) {
  config.set({
    basePath: 'src/test/js/',
    frameworks: ['jasmine'],
    files: [
      'tests.webpack.js'
    ],
    exclude: [
    ],
    preprocessors: {
      'tests.webpack.js': ['webpack']
    },
    reporters: ['mocha'],
    port: 9876,
    colors: true,
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_WARN,
    browsers: ['Chrome'],
    autoWatch: true,
    singleRun: false,
    concurrency: Infinity,
    webpack: webpackConfig
  })
}
