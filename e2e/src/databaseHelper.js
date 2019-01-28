import mysql from 'mysql2/promise'
import fs from 'fs'
import axios from 'axios'
import { ActuatorUrl } from './setupE2eTests'

let connection

export const connect = async () => {
  const dbCredentials = await retrieveDbCredentials()
  connection = await mysql.createConnection(dbCredentials)

  expect(connection).toBeDefined()
}

export const endConnection = () => {
  if (connection)
    connection.end()
}

export const resetDatabase = async () => {
  await executeSqlFile(connection, './src/sql-scripts/truncate_all.sql')
  await executeSqlFile(connection, './src/sql-scripts/dummy_data.sql')
}

const retrieveDbCredentials = async () => {
  const jdbcUrl = await getEnvValue(`${ActuatorUrl}/env/spring.datasource.url`)
  const jdbcDetails = getJdbcDetails(jdbcUrl)
  const user = await getEnvValue(`${ActuatorUrl}/env/spring.datasource.username`)
  const password = await getEnvValue(`${ActuatorUrl}/env/spring.datasource.password`)

  return {
    host: jdbcDetails.host || 'localhost',
    user: user || 'spring',
    password: password || '',
    port: jdbcDetails.port || 33060,
    database: jdbcDetails.database || 'witchcraft',
  }
}

const getEnvValue = async (url) => {
  try {
    const promise = await axios.get(url)

    if (promise.data.property.value) {
      return promise.data.property.value
    }
  } catch (error) {
    console.error(error)
  }
}

const getJdbcDetails = jdbcUrl => {
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

const executeSqlFile = async (connection, filePath) => {
  const content = fs.readFileSync(filePath).toString()
  const queries = content.split(';\n')
  for (let i = 0; i < queries.length; i++) {
    const [rows, fields] = await connection.execute(queries[i])
  }
}