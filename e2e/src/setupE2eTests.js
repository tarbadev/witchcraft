import puppeteer from 'puppeteer'
import { resetDatabase } from './databaseHelper'

let browser
export let page

const width = 1920
const height = 1080

export const AppUrl = 'http://localhost:5000'

beforeAll(async () => {
  browser = await puppeteer.launch({
    headless: false,
    slowMo: 80,
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