const validate = require('validate.js')

/** Blood Request Validation Form */
const validateRequest = (data) => {
  var constraint = {
    nama_pasien: {
      presence: {
        allowEmpty: false
      }
    },
    jml_kantong: {
      presence: {
        allowEmpty: false
      }
    },
    tipe_darah: {
      presence: {
        allowEmpty: false
      }
    },
    rhesus: {
      presence: {
        allowEmpty: false
      }
    },
    gender: {
      presence: {
        allowEmpty: false
      }
    },
    prov: {
      presence: {
        allowEmpty: false
      }
    },
    kota: {
      presence: {
        allowEmpty: false
      }
    },
    nama_rs: {
      presence: {
        allowEmpty: false
      }
    },
    deskripsi: {
      presence: {
        allowEmpty: false
      }
    },
    nama_keluarga: {
      presence: {
        allowEmpty: false
      }
    },
    telp_keluarga: {
      presence: {
        allowEmpty: false
      }
    },
  }
  return validate(data, constraint, {
    format: 'flat'
  })
}

module.exports = {
  validateRequest
}