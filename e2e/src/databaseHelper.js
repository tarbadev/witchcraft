const mysql = require('mysql2/promise')
const fs = require('fs')
require('./setupEnvironmentVariables')

class DatabaseHelper {
  async connect() {
    if (!this.connection) {
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

  async executeSqlFile(filePath) {
    const content = fs.readFileSync(filePath).toString()
    const queries = content.split(';\n')
    for (let i = 0; i < queries.length; i++) {
      const [rows, fields] = await this.connection.execute(queries[i])
    }
  }
}

module.exports = DatabaseHelper