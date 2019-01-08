import puppeteer from 'puppeteer'
import { resetDatabase } from './databaseHelper'

let browser
export let page

const width = 1920
const height = 1080

const backendUrl = process.env.BACKEND_URL ? process.env.BACKEND_URL : 'http://localhost'
export const ActuatorUrl = `${backendUrl}:8888/actuator`
export const AppUrl = process.env.APP_URL ? process.env.APP_URL : 'http://localhost:5000'

beforeAll(async () => {
  browser = await puppeteer.launch({
    headless: true,
    args: [`--window-size=${width},${height}`],
  })

  page = await browser.newPage()
  await page.setViewport({ width, height })
})

afterAll(() => {
  browser.close()
})

beforeEach(async () => {
  await resetDatabase()
})