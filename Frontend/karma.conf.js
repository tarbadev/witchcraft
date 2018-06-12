var webpackConfig = require('./webpack.config.js');

module.exports = function(config) {
  config.set({
    basePath: 'src/test/js/',
    frameworks: ['jasmine'],
    files: [
      '**/*.spec.js'
    ],
    exclude: [
    ],
    preprocessors: {
      '**/*.spec.js': ['webpack']
    },
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],
    port: 9876,
    colors: true,
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_DEBUG,
    browsers: ['Chrome'],
    autoWatch: true,
    singleRun: false,
    concurrency: Infinity,
    webpack: webpackConfig
  })
}
