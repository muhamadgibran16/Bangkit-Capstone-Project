const db = require('../../config/db')
const Sequelize = require('sequelize')

const {
  DataTypes
} = Sequelize

/** News Table */
const News = db.define('news', {
  news_id: {
    type: DataTypes.STRING,
  },
  title: {
    type: DataTypes.STRING,
  },
  url: {
    type: DataTypes.STRING,
  },
  urlImage: {
    type: DataTypes.STRING,
  },
})


module.exports = { News }