const express = require('express')

const app = express()

app.get('/api/*', (req, res) => {
  res.redirect(`${process.env.API_URL}${req.originalUrl}`)
})

app.use(express.static('.'))
app.listen(8080, () => console.log('Listening on port 8080!'))
