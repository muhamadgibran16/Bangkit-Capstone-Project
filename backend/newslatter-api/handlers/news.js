const {
  nanoid
} = require('nanoid')
const {News} = require('../../newslatter-api/models/news')


const createNews = async (req, res, next) => {
  const str = 'news-'
  const id = nanoid(10)
  const news_id = str + id
  const {
    title,
    url,
    urlImage,
  } = req.body

  try {
    await News.create({
      news_id: news_id,
      title: title,
      url: url,
      urlImage: urlImage,
    })
    console.log(News)
    res.status(200).json({
      success: true,
      message: 'News added successfully!',
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message
    })
  }
}

const getNews = async (req, res, next) => {
  try {
    const pagination = res.pagination
    const { page, perPage, offset } = pagination

    const { count, rows: news } = await News.findAndCountAll({
      attributes: ['news_id', 'title', 'url', 'urlImage', 'createdAt'],
      limit: perPage,
      offset: offset,
    })

    const totalPages = Math.ceil(count / perPage)
    pagination.hasNextPage = page < totalPages
    pagination.hasPreviousPage = page > 1
    pagination.nextPage = page + 1
    pagination.previousPage = page - 1

    console.log('Nesnews => ', news)
    if (news.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'News not found!'
      })
    }

    res.status(200).json({
      success: true,
      message: 'News retrieved successfully!',
      payload: news,
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

module.exports = { createNews, getNews }