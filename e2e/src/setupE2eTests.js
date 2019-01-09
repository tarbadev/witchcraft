import puppeteer from 'puppeteer'
import { resetDatabase } from './databaseHelper'

let browser
export let page

const width = 1920
const height = 1080

const backendUrl = process.env.BACKEND_URL ? process.env.BACKEND_URL : 'http://localhost:8080'
export const ActuatorUrl = `${backendUrl}/actuator`
export const AppUrl = process.env.APP_URL ? process.env.APP_URL : 'http://localhost:5000'

beforeAll(async (done) => {
  browser = await puppeteer.launch({
    headless: true,
    args: [`--window-size=${width},${height}`, '-–no-sandbox', '-–disable-setuid-sandbox'],
  })

  page = await browser.newPage()
  await page.setViewport({ width, height })

  done()
})

afterAll(() => {
  if (browser)
    browser.close()
})

beforeEach(async () => {
  await resetDatabase()
})