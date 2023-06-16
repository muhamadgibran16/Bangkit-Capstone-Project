const {
  Requests,
  Donor
} = require("../models/donorModel")

/** Get Blood Requests History */
const getBloodRequestsHistory = async (req, res, next) => {
  try {
    const pagination = res.pagination
    const { page, perPage, offset } = pagination

    const {count, rows: history} = await Requests.findAndCountAll({
      attributes: ['createdBy', 'id_request', 'nama_pasien', 'jml_kantong', 'tipe_darah', 'rhesus', 'gender', 'prov', 'kota', 'nama_rs', 'deskripsi', 'nama_keluarga', 'telp_keluarga', 'createdAt'],
      where: {
        createdBy: req.uid
      },
      limit: perPage,
      offset: offset,
    })

    const totalPages = Math.ceil(count / perPage)
    pagination.hasNextPage = page < totalPages
    pagination.hasPreviousPage = page > 1
    pagination.nextPage = page + 1
    pagination.previousPage = page - 1

    console.log('History => ', history)
    if (history.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'History blood requests not found!'
      })
    }

    res.status(200).json({
      success: true,
      message: 'History blood request retrieved successfully!',
      payload: history,
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

/** Get Donor Requests History */
const getDonorRequestsHistory = async (req, res, next) => {
  try {
    const pagination = res.pagination
    const { page, perPage, offset } = pagination

    const {count, rows: history} = await Donor.findAndCountAll({
      attributes: ['uid', 'id_donor', 'nama_pendonor', 'alamat', 'telp', 'gol_darah', 'rhesus', 'last_donor', 'nama_rs', 'alamat_rs', 'nama_pasien'],
      where: {
        uid: req.uid
      },
      limit: perPage,
      offset: offset,
    })

    const totalPages = Math.ceil(count / perPage)
    pagination.hasNextPage = page < totalPages
    pagination.hasPreviousPage = page > 1
    pagination.nextPage = page + 1
    pagination.previousPage = page - 1

    if (history.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'History donor requests not found!'
      })
    }
    console.log('History => ', history)
    res.status(200).json({
      success: true,
      message: 'History donor request retrieved successfully!',
      payload: history,
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

module.exports = {
  getBloodRequestsHistory,
  getDonorRequestsHistory
}