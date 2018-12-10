import mysql from '@mysql/xdevapi'
import fs from 'fs'

export const resetDatabase = async () => {
  const session = await mysql.getSession({
    host: 'localhost',
    user: 'spring',
    password: '',
    port: 33060,
  })

  session.sql('USE witchcraft').execute()

  executeSqlFile(session, './src/sql-scripts/truncate_all.sql')
  executeSqlFile(session, './src/sql-scripts/dummy_data.sql')
}

const executeSqlFile = (session, filePath) => {
  const content = fs.readFileSync(filePath).toString()
  const queries = content
    .split(';')
    .filter(query => query !== '')
  queries.forEach(query => session.sql(query).execute())
}