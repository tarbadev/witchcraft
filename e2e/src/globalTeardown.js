const { teardown: teardownPuppeteer } = require('jest-environment-puppeteer')

module.exports = async function globalTeardown(globalConfig) {
  await teardownPuppeteer(globalConfig)

  if (global.browser)
    await global.browser.close()

  global.databaseHelper.endConnection()
}