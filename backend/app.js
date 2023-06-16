require('@google-cloud/debug-agent').start()
require('dotenv').config()
const express = require('express')
const app = express()
const cookieParser = require('cookie-parser')
const cors = require('cors')
const morgan = require('morgan')
const db = require('./config/db.js')
const authRouter = require('./auth-api/routers/index')
const donorRouter = require('./donor-api/routers/index')
const newslatter = require('./newslatter-api/routers/index')

app.use(cookieParser())
app.use(cors({
  credentials: true,
  origin: '*'
}))
app.use(morgan('tiny'))
app.disable('x-powered-by') 
app.use(express.json())
app.use('/v1',authRouter)
app.use('/v1', donorRouter)
app.use('/v1', newslatter)

app.get('/', (req, res) => {
  console.log('Response success')
  res.send('Gokil Mantul Ngebug Njlimet Nyenyenye!')
})

const PORT = process.env.PORT || 8080
app.listen(PORT, () => {
  console.log(`Server Running on http://localhost:${PORT}`)
})