const validate = require('validate.js')

/** Validate Registration Form */
const validateRegister = (data) => {
  var constraint = {
    name: {
      presence: {
        allowEmpty: false
      }
    },
    email: {
      presence: {
        allowEmpty: false
      }
    },
    telp: {
      presence: {
        allowEmpty: false
      }
    },
    password: {
      presence: {
        allowEmpty: false
      }
    },
  }
  return validate(data, constraint, {
    format: 'flat'
  })
}

/** Validate New Password */
const validateNewPassword = (data) => {
  var constraint = {
    newPassword: {
      presence: {
        allowEmpty: false
      }
    }
  }
  return validate(data, constraint, {
    format: 'flat'
  })
}

module.exports = {
  validateRegister,
  validateNewPassword
}