const mysql = require('mysql2/promise')
const fs = require('fs')
const axios = require('axios')
require('./setupEnvironmentVariables')

class DatabaseHelper {
  async connect() {
    if (!this.connection) {
      const dbCredentials = await DatabaseHelper.retrieveDbCredentials()

      const timeout = 30
      let start = new Date()
      console.debug(`Connect to DB. It will timeout after ${timeout} seconds`)
      while (!this.connection && DatabaseHelper.secondsFromStart(start) < timeout) {
        try {
          this.connection = await mysql.createConnection(dbCredentials)
        } catch ({ message }) {
          console.error(`Failed connecting to database:${message}`)
          console.error(`Timeout in: ${timeout - DatabaseHelper.secondsFromStart(start)} seconds`)
        }
      }
    }

    return this.connection
  }

  static secondsFromStart(start) {
    return (new Date() - start) / 1000
  }

  endConnection() {
    if (this.connection) {
      this.connection.end()
    }
  }

  async resetDatabase() {
    await this.executeSqlFile('./src/sql-scripts/truncate_all.sql')
    await this.executeSqlFile('./src/sql-scripts/dummy_data.sql')
  }

  static async retrieveDbCredentials() {
    const jdbcUrl = await DatabaseHelper.getEnvValue(`${global.actuatorUrl}/env/spring.datasource.url`)
    const jdbcDetails = DatabaseHelper.getJdbcDetails(jdbcUrl)
    const user = await DatabaseHelper.getEnvValue(`${global.actuatorUrl}/env/spring.datasource.username`)
    const password = await DatabaseHelper.getEnvValue(`${global.actuatorUrl}/env/spring.datasource.password`)

    return {
      host: jdbcDetails.host || 'localhost',
      user: user || 'spring',
      password: password || '',
      port: jdbcDetails.port || 33060,
      database: jdbcDetails.database || 'witchcraft',
    }
  }

  async executeSqlFile(filePath) {
    const content = fs.readFileSync(filePath).toString()
    const queries = content.split(';\n')
    for (let i = 0; i < queries.length; i++) {
      const [rows, fields] = await this.connection.execute(queries[i])
    }
  }

  static async getEnvValue(url) {
    try {
      const promise = await axios.get(url)

      if (promise.data.property.value) {
        return promise.data.property.value
      }
    } catch (error) {
      console.error(`Error while getting environment value for ${url}`)
    }
  }

  static getJdbcDetails(jdbcUrl) {
    const jdbcDetails = {}

    if (jdbcUrl) {
      const regex = /jdbc:mysql:\/\/(.*):(\d+)\/(.*)\?.*/g
      const matches = regex.exec(jdbcUrl)
      jdbcDetails.host = matches[1]
      jdbcDetails.port = matches[2]
      jdbcDetails.database = matches[3]
    }

    return jdbcDetails
  }
}

module.exports = DatabaseHelper