const Sequelize = require('sequelize')

/** SQL Connectiion */
const db = new Sequelize(process.env.DB_NAME, process.env.DB_USERNAME, process.env.DB_PASS, {
  host: process.env.DB_HOST,
  dialect: "mysql",
  logging: false,
  multipleStatements: true,
})

try {
  db.authenticate()
  console.log('Database Connected!')
  db.sync()
} catch (err) {
  console.error(err)
}

module.exports = db