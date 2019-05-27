require('expect-puppeteer')
require('@babel/polyfill')
require('./setupEnvironmentVariables')

global.beforeEach(async () => {
  await global.databaseHelper.resetDatabase()
})