const history = require('connect-history-api-fallback')
const express = require('express')

const app = express()

app.all('/api/*', (req, res) => {
  res.redirect(307, `${process.env.API_URL}${req.originalUrl}`)
})

app.use(history({ verbose: false }))
app.use(express.static('.'))
app.listen(8080, () => console.log('Listening on port 8080!'))
