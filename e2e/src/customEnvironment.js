const PuppeteerEnvironment = require('jest-environment-puppeteer')

class CustomEnvironment extends PuppeteerEnvironment {
  async setup() {
    await super.setup()
    this.global.databaseHelper = global.databaseHelper
  }
}

module.exports = CustomEnvironment