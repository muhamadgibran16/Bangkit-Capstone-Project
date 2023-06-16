const db = require('../../config/db')
const Sequelize = require('sequelize')

const {
  DataTypes
} = Sequelize

/** Province Table */
const Province = db.define('province', {
  provinsi: {
    type: DataTypes.STRING,
  },
})

/** City Table */
const City = db.define('city', {
  id_prov: {
    type: DataTypes.INTEGER,
  },
  city: {
    type: DataTypes.STRING,
  },
})

/** Hospital Table */
const Hospital = db.define('hospital', {
  id_city: {
    type: DataTypes.INTEGER,
  },
  nama_rs: {
    type: DataTypes.STRING,
  },
  alamat_rs: {
    type: DataTypes.STRING,
  },
  telp_rs: {
    type: DataTypes.STRING,
  },
  koordinat: {
    type: DataTypes.STRING,
  },
  latitude: {
    type: DataTypes.STRING,
  },
  longitude: {
    type: DataTypes.STRING,
  },
})

/** Blood Requests Table */
const Requests = db.define('blood_request', {
  id_request: {
    type: DataTypes.STRING,
  },
  nama_pasien: {
    type: DataTypes.STRING,
  },
  jml_kantong: {
    type: DataTypes.INTEGER,
  },
  tipe_darah: {
    type: DataTypes.STRING,
  },
  rhesus: {
    type: DataTypes.STRING,
  },
  gender: {
    type: DataTypes.STRING,
  },
  prov: {
    type: DataTypes.STRING,
  },
  kota: {
    type: DataTypes.STRING,
  },
  nama_rs: {
    type: DataTypes.STRING,
  },
  deskripsi: {
    type: DataTypes.STRING,
  },
  nama_keluarga: {
    type: DataTypes.STRING,
  },
  telp_keluarga: {
    type: DataTypes.STRING,
  },
  createdBy: {
    type: DataTypes.STRING,
  },
  verified: {
    type: DataTypes.BOOLEAN,
    defaultValue: false
  },
})

/** Blood Types Table */
const BloodType = db.define('blood_type', {
  tipe_darah: {
    type: DataTypes.STRING,
  }
})

/** Rhesus Table */
const Rhesus = db.define('rhesu', {
  rhesus: {
    type: DataTypes.STRING,
  }
})

/** Blood Stock Table */
const Stock = db.define('stock', {
  id_rs: {
    type: DataTypes.INTEGER,
  },
  id_darah: {
    type: DataTypes.INTEGER,
  },
  id_rhesus: {
    type: DataTypes.INTEGER,
  },
  stock: {
    type: DataTypes.INTEGER,
  },
})

/** Donor Table */
const Donor = db.define('donor', {
  uid: {
    type: DataTypes.STRING,
  },
  id_donor: {
    type: DataTypes.STRING,
  },
  id_request: {
    type: DataTypes.STRING
  },
  nama_pendonor: {
    type: DataTypes.STRING,
  },
  alamat: {
    type: DataTypes.STRING,
  },
  telp: {
    type: DataTypes.STRING,
  },
  gol_darah: {
    type: DataTypes.STRING,
  },
  rhesus: {
    type: DataTypes.STRING,
  },
  last_donor: {
    type: DataTypes.DATE,
  },
  nama_rs: {
    type: DataTypes.STRING,
  },
  alamat_rs: {
    type: DataTypes.STRING,
  },
  nama_pasien: {
    type: DataTypes.STRING,
  },
  verified: {
    type: DataTypes.BOOLEAN,
    defaultValue: false
  }
})

module.exports = {
  Province,
  City,
  Hospital,
  Requests,
  Donor,
  BloodType,
  Rhesus,
  Stock
}