import puppeteer from 'puppeteer'
import { connect, endConnection, resetDatabase } from './databaseHelper'

let browser
export let page

const width = 1920
const height = 1080

const backendUrl = process.env.BACKEND_URL ? process.env.BACKEND_URL : 'http://localhost:8080'
export const ActuatorUrl = `${backendUrl}/actuator`
export const AppUrl = process.env.APP_URL ? process.env.APP_URL : 'http://localhost:5000'

beforeAll(async () => {
  await connect()
  browser = await puppeteer.launch({
    headless: false,
    args: [`--window-size=${width},${height}`, '--no-sandbox', '--disable-setuid-sandbox'],
  })

  page = await browser.newPage()
  await page.setViewport({ width, height })
})

afterAll(() => {
  if (browser)
    browser.close()

  endConnection()
})

beforeEach(async () => {
  await resetDatabase()
})