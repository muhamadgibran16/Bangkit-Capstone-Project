const {
  URL
} = require('url')

const Pagination = async (req, res, next) => {
  // Default Page Number
  let page = parseInt(req.query.page) || 1
  // Data display size of the page
  let size = parseInt(req.query.size) || 10
  let perPage = size

  if (page === 0) {
    page = 0;
    perPage = 1000;
  }

  const offset = (page - 1) * perPage

  const baseUrl = req.protocol + '://' + req.get('host') + req.originalUrl

  const objectUrl = new URL(baseUrl)
  objectUrl.searchParams.delete('page')
  const changeUrl = objectUrl.toString()

  res.pagination = {
    page: page,
    perPage: perPage,
    offset: offset,
    hasNextPage: false,
    hasPreviousPage: false,
    nextPage: null,
    previousPage: null,
    getPreviousLink: () => {
      if (res.pagination.hasPreviousPage) {
        return `${changeUrl}?page=${res.pagination.previousPage}`
      }
      return null
    },
    getNextLink: () => {
      if (res.pagination.hasNextPage) {
        return `${changeUrl}?page=${res.pagination.nextPage}`
      }
      return null
    }
  }
  next()
}

module.exports = {
  Pagination
}