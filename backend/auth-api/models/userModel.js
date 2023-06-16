const db = require('../../config/db')
const Sequelize = require('sequelize')

const {
  DataTypes
} = Sequelize

/** Users Table */
const Users = db.define('users', {
  uid: {
    type: DataTypes.STRING,
  },
  name: {
    type: DataTypes.STRING,
  },
  email: {
    type: DataTypes.STRING,
  },
  telp: {
    type: DataTypes.STRING,
  },
  password: {
    type: DataTypes.STRING,
  },
  nik: {
    type: DataTypes.STRING,
  },
  alamat: {
    type: DataTypes.STRING,
  },
  ttl: {
    type: DataTypes.STRING,
  },
  gol_darah: {
    type: DataTypes.STRING,
  },
  rhesus: {
    type: DataTypes.STRING,
  },
  gender: {
    type: DataTypes.STRING,
  },
  last_donor: {
    type: DataTypes.DATE,
  },
  photo: {
    type: DataTypes.STRING,
  },
  refresh_token: {
    type: DataTypes.TEXT,
  },
  verified: {
    type: DataTypes.BOOLEAN,
    defaultValue: false
  },
  ktp: {
    type: DataTypes.BOOLEAN,
    defaultValue:false
  }
})

/** OTP Table */
const OtpVerification = db.define('otp', {
  uid: {
    type: DataTypes.STRING,
  },
  otp: {
    type: DataTypes.STRING,
  },
  createdAt: {
    type: DataTypes.DATE,
  },
  expiredAt: {
    type: DataTypes.DATE,
  }
})

module.exports = {
  Users,
  OtpVerification,
}