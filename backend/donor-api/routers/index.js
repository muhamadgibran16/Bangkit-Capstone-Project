const express = require('express')
const router = express.Router()
const {
  verifyToken
} = require('../../auth-api/middleware/auth')
const {
  createBloodRequest,
  createBloodDonor
} = require('../../donor-api/handlers/users')
const list = require('../../donor-api/handlers/list')
const location = require('../../donor-api/handlers/location')
const history = require('../../donor-api/handlers/history')
const {Pagination} = require('../../utils/pagination')

router.use(Pagination)

/** Users Request */
router.post('/request', verifyToken, createBloodRequest)
router.post('/donor', verifyToken, createBloodDonor)

/** Users History */
router.get('/blood-history', verifyToken, history.getBloodRequestsHistory)
router.get('/donor-history', verifyToken, history.getDonorRequestsHistory)

/** List App */
router.get('/list/all-request', verifyToken, list.getListAllRequest)
router.get('/list/blood-request', verifyToken, list.getListBloodRequests)
router.get('/list/blood-request', verifyToken, list.getListBloodRequests)
router.get('/list/detail-request/:id', verifyToken, list.getDetailRequestById)
router.get('/list/all-stock', verifyToken, list.getAllStock)
router.get('/list/stock/:id', verifyToken, list.getStockByBloodTypeId)
router.get('/list/stock/type/:typeid/rhesus/:rhesusid', verifyToken, list.getStockByBloodTypeAndRhesus)
router.get('/list/filter-data/:nama_rs', verifyToken, list.filteringDataPatient)
router.get('/list/filter-all-data', verifyToken, list.filteringAllDataPatient)


/** Location */
router.get('/province', location.getProvince)
router.get('/province/city', location.getAllCity)
router.get('/province/city/:id', location.getCityByIdProvince)
router.get('/hospital', location.getAllHospital)
router.get('/province/city/hospital/:id', location.getHospitalByIdCity)
router.get('/province/city/hospital/location/:id', location.getHospitalLocationById)

module.exports = router