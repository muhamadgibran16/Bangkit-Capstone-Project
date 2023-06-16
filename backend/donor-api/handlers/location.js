const {
  Province,
  City,
  Hospital,
} = require('../../donor-api/models/donorModel')

/** Get All Data Province */
const getProvince = async (req, res, next) => {
  try {
    const province = await Province.findAll({
      attributes: ['id', 'provinsi']
    })

    if (province.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'Data not found!'
      })
    }
    res.status(200).json({
      success: true,
      message: 'Province data retrieved successfully',
      payload: province
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message,
    })
  }
}

/** Get All Data City */
const getAllCity = async (req, res, next) => {
  try {
    const city = await City.findAll({
      attributes: ['id', 'id_prov', 'city']
    })

    if (city.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'Data not found!'
      })
    }
    res.status(200).json({
      success: true,
      message: 'City data retrieved successfully!',
      payload: city
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message,
    })
  }
}

/** Get City By Province ID */
const getCityByIdProvince = async (req, res, next) => {
  try {
    City.belongsTo(Province, {
      foreignKey: 'id_prov',
    })
    Province.hasMany(City, {
      foreignKey: 'id_prov',
    })
    const city = await City.findAll({
      attributes: ['id', 'id_prov', 'city'],
      where: {
        id_prov: req.params.id
      }
    })
    if (city.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'Data not found!'
      })
    }
    res.status(200).json({
      success: true,
      message: 'City data retrieved successfully by Province ID!',
      payload: city
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message,
    })
  }
}

/** Get All Data Hospitals */
const getAllHospital = async (req, res, next) => {
  try {
    const hospital = await Hospital.findAll({
      attributes: ['id', 'id_city', 'nama_rs', 'alamat_rs', 'telp_rs', 'koordinat', 'latitude', 'longitude']
    })

    console.log('hospital => ', hospital)
    if (hospital.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'Data not found!'
      })
    }
    res.status(200).json({
      success: true,
      message: 'Hospital data retrieved successfully!',
      payload: hospital
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message,
    })
  }
}

/** Get All Data Hospitals By City ID */
const getHospitalByIdCity = async (req, res, next) => {
  try {
    Hospital.belongsTo(City, {
      foreignKey: 'id_city',
    })
    City.hasMany(Hospital, {
      foreignKey: 'id_city',
    })
    const hospital = await Hospital.findAll({
      // include: [{
      //   model: City,
      //   attributes: ['id']
      // }],
      attributes: ['id', 'id_city', 'nama_rs', 'alamat_rs', 'telp_rs', 'koordinat', 'latitude', 'longitude'],
      where: {
        id_city: req.params.id
      }
    })

    if (hospital.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'Data not found!'
      })
    }
    res.status(200).json({
      success: true,
      message: 'Hospital data retrieved successfully By City ID!',
      payload: hospital
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message,
    })
  }
}

/** Get Data Hospital Location to display marker points */
const getHospitalLocationById = async (req, res, next) => {
  try {
    const hospital = await Hospital.findAll({
      attributes: ['id', 'nama_rs', 'alamat_rs', 'latitude', 'longitude'],
      where: {
        id: req.params.id,
      }
    })

    if (hospital.length === 0) {
      return res.status(404).json({
        success: false,
        message: 'Data not found!'
      })
    }
    res.status(200).json({
      success: true,
      message: 'Hospital location retrieved successfully By ID!',
      payload: hospital
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
  getProvince,
  getAllCity,
  getCityByIdProvince,
  getAllHospital,
  getHospitalByIdCity,
  getHospitalLocationById
}