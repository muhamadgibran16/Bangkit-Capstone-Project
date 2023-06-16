const express = require('express')
const router = express.Router()
const {Pagination} = require('../../utils/pagination')
const news = require('../../newslatter-api/handlers/news')
const event = require('../../newslatter-api/handlers/event')
const {
  verifyToken
} = require('../../auth-api/middleware/auth')

router.use(Pagination)

/** News */
router.post('/add-news', news.createNews)
router.get('/get-news', verifyToken, news.getNews)

/** Event */
router.post('/add-event', event.createEvent)
router.get('/get-event', verifyToken, event.getEvent)

module.exports = router