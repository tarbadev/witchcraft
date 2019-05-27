require('@babel/polyfill')
const puppeteer = require('puppeteer')
const DatabaseHelper = require('./databaseHelper')

const { setup: setupPuppeteer } = require('jest-environment-puppeteer')

module.exports = async function globalSetup(globalConfig) {
  await setupPuppeteer(globalConfig)

  global.databaseHelper = new DatabaseHelper()
  await global.databaseHelper.connect()

  global.width = 1920
  global.height = 1080

  global.browser = await puppeteer.launch({
    headless: true,
    args: [`--window-size=${global.width},${global.height}`, '--no-sandbox', '--disable-setuid-sandbox'],
  })
}