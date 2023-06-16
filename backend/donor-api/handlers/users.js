const {
  nanoid
} = require('nanoid')
const {
  Requests,
  Donor,
} = require('../../donor-api/models/donorModel')
const {
  validateRequest
} = require('../../donor-api/utils/formValidation')


/** Users make a blood request through the application */
const createBloodRequest = async (req, res, next) => {
  const str = 'req-'
  const id = nanoid(10)
  const id_request = str + id
  const {
    nama_pasien,
    jml_kantong,
    tipe_darah,
    rhesus,
    gender,
    prov,
    kota,
    nama_rs,
    deskripsi,
    nama_keluarga,
    telp_keluarga,
    createdBy,
  } = req.body

  var err = validateRequest(req.body)
  if (err) {
    return res.status(400).json({
      success: false,
      message: 'Field must not be empty!'
    })
  }

  try {
    await Requests.create({
      id_request: id_request,
      nama_pasien: nama_pasien,
      jml_kantong: jml_kantong,
      tipe_darah: tipe_darah,
      rhesus: rhesus,
      gender: gender,
      prov: prov,
      kota: kota,
      nama_rs: nama_rs,
      deskripsi: deskripsi,
      nama_keluarga: nama_keluarga,
      telp_keluarga: telp_keluarga,
      createdBy: createdBy,
      verified: false,
    })
    console.log(Requests)
    res.status(200).json({
      success: true,
      message: 'Request created successfully!',
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message
    })
  }
}

/** Users make donor request through the application */
const createBloodDonor = async (req, res, next) => {
  const str = 'donor-'
  const id = nanoid(10)
  const id_donor = str + id
  const {
    uid,
    nama_pendonor,
    alamat,
    telp,
    gol_darah,
    rhesus,
    last_donor,
    nama_rs,
    alamat_rs,
    nama_pasien,
  } = req.body

  try {
    await Donor.create({
      uid: uid,
      id_donor: id_donor,
      nama_pendonor: nama_pendonor,
      alamat: alamat,
      telp: telp,
      gol_darah: gol_darah,
      rhesus: rhesus,
      last_donor: last_donor,
      nama_rs: nama_rs,
      alamat_rs: alamat_rs,
      nama_pasien: nama_pasien,
      verified: false,
    })
    console.log(Donor)
    res.status(200).json({
      success: true,
      message: 'Blood donor created successfully!',
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message
    })
  }
}

module.exports = {
  createBloodRequest,
  createBloodDonor,
}