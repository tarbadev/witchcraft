global.appUrl = process.env.APP_URL ? process.env.APP_URL : 'http://localhost:5000'

if (process.env.DB_CREDENTIALS) {
  const lines = process.env.DB_CREDENTIALS.split('\n')
  if (!lines[0].includes('{')) {
    lines.splice(0, 1)
  }

  const credentials = JSON.parse(lines.join('\n'))

  global.dbCredentials = {
    host: credentials.hostname,
    user: credentials.username,
    password: credentials.password,
    port: credentials.port,
    database: credentials.name,
  }
} else {
  global.dbCredentials = {
    host: 'localhost',
    user: 'spring',
    password: '',
    database: 'witchcraft',
  }
}