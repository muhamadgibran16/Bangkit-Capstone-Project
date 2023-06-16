const {
  nanoid
} = require('nanoid')
const { Event } = require('../../newslatter-api/models/event')


const createEvent = async (req, res, next) => {
  const str = 'event-'
  const id = nanoid(10)
  const event_id = str + id
  const {
    title,
    url,
    urlImage,
  } = req.body

  try {
    await Event.create({
      event_id: event_id,
      title: title,
      url: url,
      urlImage: urlImage,
    })
    console.log(Event)
    res.status(200).json({
      success: true,
      message: 'Event added successfully!',
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message
    })
  }
}

const getEvent = async (req, res, next) => {
  try {
    const pagination = res.pagination
    const { page, perPage, offset } = pagination

    const { count, rows: event } = await Event.findAndCountAll({
      attributes: ['event_id', 'title', 'url', 'urlImage', 'createdAt'],
      limit: perPage,
      offset: offset,
    })

    const totalPages = Math.ceil(count / perPage)
    pagination.hasNextPage = page < totalPages
    pagination.hasPreviousPage = page > 1
    pagination.nextPage = page + 1
    pagination.previousPage = page - 1

    console.log('event => ', event)
    if (event.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'Event not found!'
      })
    }

    res.status(200).json({
      success: true,
      message: 'Event retrieved successfully!',
      payload: event,
      pagination: {
        page,
        perPage,
        totalItems: count,
        totalPages,
        previousLink: pagination.getPreviousLink(),
        nextLink: pagination.getNextLink(),
      },
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message,
    })
  }
}

module.exports = { createEvent, getEvent }