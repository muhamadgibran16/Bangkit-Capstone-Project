const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')
const nodemailer = require('nodemailer')
const otpGenerator = require('otp-generator')
const Mailgen = require('mailgen')
const {
  nanoid
} = require('nanoid')
const {
  Users,
  OtpVerification
} = require('../../auth-api/models/userModel')
const {
  validateRegister,
  validateNewPassword
} = require('../../auth-api/utils/userValidation')
require('dotenv').config()

/** Register */
const register = async (req, res, next) => {
  const str = 'user-'
  const id = nanoid(10)
  const uid = str + id
  const {
    name,
    email,
    telp,
    password,
    confirmPassword,
  } = req.body

  const existEmail = await Users.findOne({
    where: {
      email
    }
  })

  if (existEmail) {
    return res.status(409).json({
      success: false,
      message: 'Email is already exists!',
    })
  }
  if (!email.includes('@')) {
    return res.status(400).json({
      success: false,
      message: 'Email format is invalid!'
    })
  }
  if (password.length < 8) {
    return res.status(403).json({
      success: false,
      message: 'Password must be at least 8 characters!'
    })
  }

  var err = validateRegister(req.body)
  if (err) {
    return res.status(400).json({
      success: false,
      message: 'Field must not be empty!'
    })
  }

  if (password !== confirmPassword) {
    return res.status(403).json({
      success: false,
      message: 'Password and confirm password don\'t match!'
    })
  }

  const salt = await bcrypt.genSalt()
  const hashPassword = await bcrypt.hash(password, salt)
  try {
    const user = await Users.create({
      uid: uid,
      name: name,
      email: email,
      telp: telp,
      password: hashPassword,
      verified: false,
    })

    generateOTP(user, uid, res)

    return res.json({
      success: true,
      message: 'Register success, please verify your account!',
      payload: {
        uid,
      }
    })
  } catch {
    console.log(err)
    res.status(400).json({
      success: false,
      message: err.message
    })
  }
}

/** Login */
const login = async (req, res, next) => {
  try {
    const user = await Users.findOne({
      where: {
        email: req.body.email,
      }
    })

    if (!user) {
      return res.status(401).json({
        success: false,
        message: 'Email not registered!',
      })
    }
    const uid = user.uid
    const name = user.name
    const email = req.body.email
    const match = await bcrypt.compare(req.body.password, user.password)
    if (!match) {
      return res.status(401).json({
        success: false,
        message: 'Wrong Password!'
      })
    }
    if (!user.verified) {
      return res.status(403).json({
        success: false,
        message: 'Email not verified!',
        payload: {
          uid: uid
        }
      })
    }

    const accessToken = jwt.sign({
        uid,
        name,
        email,
      },
      process.env.ACCESS_TOKEN_SECRET_KEY, {
        expiresIn: '24h'
      })
    const refreshToken = jwt.sign({
        uid,
        name,
        email,
      },
      process.env.REFRESH_TOKEN_SECRET_KEY, {
        expiresIn: '7d'
      })
    await Users.update({
      refresh_token: refreshToken
    }, {
      where: {
        uid: uid
      }
    })
    res.cookie('refreshToken', refreshToken, {
      httpOnly: true,
      maxAge: 24 * 60 * 60 * 1000,
      samSite: 'none',
      secure: false,
    })
    res.status(200).json({
      success: true,
      message: 'Login Successfully!',
      payload: {
        uid,
        name,
        token: accessToken,
      }
    })
  } catch (err) {
    console.log(err)
    res.status(401).json({
      success: false,
      message: err.message,
    })
  }
}

/** Logout */
const logout = async (req, res, next) => {
  const refreshToken = req.cookies.refreshToken
  if (!refreshToken) {
    return res.status(204).json({
      success: true,
      message: 'No content available'
    })
  }
  const user = await Users.findAll({
    where: {
      refresh_token: refreshToken
    },
  })
  if (!user[0]) {
    return res.status(204).json({
      success: true,
      message: 'No content available!'
    })
  }
  const uid = user[0].uid
  await Users.update({
    refresh_token: null
  }, {
    where: {
      uid: uid
    }
  })
  res.clearCookie('refreshToken')
  return res.status(200).json({
    success: true,
    message: 'Logout Success!'
  })
}

/** Generate OTP Code */
const generateOTP = async ({
  uid,
  name,
  email
}) => {

  try {
    const mailConfig = {
      service: 'gmail',
      host: 'smtp.gmail.com',
      auth: {
        user: process.env.MAIL_USERNAME,
        pass: process.env.MAIL_PASSWORD,
      },
    }
    const transporter = nodemailer.createTransport(mailConfig)
    const mailGenerator = new Mailgen({
      theme: 'default',
      product: {
        name: 'Donor Go',
        link: 'http://mailgen.js',
      },
      content: {
        footer: {
          greeting: 'Best Regards',
          name: 'Donor App',
        },
      },
    })
    otp = otpGenerator.generate(6, {
      digits: true,
      lowerCaseAlphabets: false,
      upperCaseAlphabets: false,
      specialChars: false,
    })
    const sendMail = {
      body: {
        name: name,
        intro: 'Selamat datang di Donor Go! Kami sangat senang Anda bergabung!',
        outro: `Demi keamanan Anda, Silahkan masukan kode otp untuk memverifikasi akun anda <b>${otp}</b>`,
      },
    }
    const emailBody = mailGenerator.generate(sendMail)
    console.log('otpCode => ', otp)
    const message = {
      from: process.env.MAIL_USERNAME,
      to: email,
      subject: 'Verifikasi OTP',
      html: emailBody,
    }
    const salt = await bcrypt.genSalt()
    const hashOTP = await bcrypt.hash(otp, salt)

    const expiredDate = new Date()
    expiredDate.setSeconds(expiredDate.getSeconds() + 3600000)
    await OtpVerification.create({
      uid: uid,
      otp: hashOTP,
      createdAt: new Date(),
      expiredAt: expiredDate,
    })
    await transporter.sendMail(message)
  } catch (err) {
    console.log(err)
  }
}

/** Verify OTP Code */
const verifyOTP = async (req, res, next) => {
  try {
    const {
      uid,
      otp
    } = req.body

    if (!otp || !uid) {
      res.status(400).json({
        success: false,
        message: 'OTP details are not allowed!',
      })
    } else {
      const otpCode = await OtpVerification.findOne({
        where: {
          uid,
        },
      })
      if (!otpCode) {
        res.status(403).json({
          success: false,
          message: 'Account record doesn\'t exist or has been verified already, please sign up or login!',
        })
      } else {
        const expiredAt = otpCode.expiredAt
        const hashOTP = otpCode.otp
        if (expiredAt < Date.now()) {
          await OtpVerification.destroy({
            where: {
              uid,
            },
          })
          res.status(400).json({
            success: false,
            message: 'OTP has expired, please request again!',
          })
        } else {
          const validOTP = await bcrypt.compare(otp, hashOTP)
          if (!validOTP) {
            res.status(400).json({
              success: false,
              message: 'Invalid OTP, Please check your inbox!',
            })
          } else {
            await Users.update({
              verified: true
            }, {
              where: {
                uid: uid,
              },
            })
            await OtpVerification.destroy({
              where: {
                uid,
              },
            })
            res.status(201).json({
              success: true,
              message: 'Your account verified successfully!',
            })
          }
        }
      }
    }
  } catch (err) {
    console.log(err)
    res.status(401).json({
      success: false,
      message: err.message,
    })
  }
}

/** Resend OTP Code  */
const reSendOTP = async (req, res, next) => {
  try {
    const {
      uid,
      email
    } = req.body
    if (!uid || !email) {
      return res.status(400).json({
        success: false,
        message: 'Empty user details are not allowed!',
      })
    }

    const otpCode = await OtpVerification.findOne({
      where: {
        uid: uid,
      },
    })

    if (!otpCode) {
      return res.status(404).json({
        success: false,
        message: 'No existing OTP record found!',
      })
    }

    await OtpVerification.destroy({
      where: {
        uid: uid,
      },
    })

    generateOTP({
      uid,
      name: otpCode.name,
      email
    }, req, res)

    res.status(201).json({
      success: true,
      message: 'OTP has been resent successfully!',
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: 'Failed to resend OTP!',
    })
  }
}

/** Forgot Password */
const forgotPassword = async (req, res, next) => {
  const mailConfig = {
    service: 'gmail',
    host: 'smtp.gmail.com',
    auth: {
      user: process.env.MAIL_USERNAME,
      pass: process.env.MAIL_PASSWORD,
    },
  }
  const transporter = nodemailer.createTransport(mailConfig)
  const mailGenerator = new Mailgen({
    theme: 'default',
    product: {
      name: 'Donor Go',
      link: 'http://mailgen.js',
    },
    content: {
      footer: {
        greeting: 'Best Regards',
        name: 'Donor App',
      },
    },
  })
  try {
    const {
      email
    } = req.body
    if (!email) {
      return res.status(400).json({
        success: false,
        message: 'Email is required!',
      })
    }
    const user = await Users.findOne({
      where: {
        email: email,
      },
    })
    if (!user) {
      return res.status(404).json({
        success: false,
        message: 'User Not Found!',
      })
    }
    const accessToken = jwt.sign({
        email: email,
      },
      process.env.ACCESS_TOKEN_SECRET_KEY, {
        expiresIn: '1h',
      }
    )
    console.log('token => ', accessToken)
    await Users.update({
      refresh_token: accessToken,
    }, {
      where: {
        email: email,
      }
    })
    const name = user.name
    const sendMail = {
      body: {
        name: name,
        intro: 'Untuk memastikan ini adalah anda',
        outro: `Silakan klik tautan berikut untuk mereset password Anda: <a href="https://donor-go-m6zxt5yvja-et.a.run.app/v1/reset-password/${accessToken}">https://donor-go-m6zxt5yvja-et.a.run.app/v1/reset-password/${accessToken}</a>`,
      },
    }
    const emailBody = mailGenerator.generate(sendMail)
    const message = {
      from: process.env.MAIL_USERNAME,
      to: email,
      subject: 'Reset password',
      html: emailBody,
    }
    await transporter.sendMail(message)
    return res.status(200).json({
      success: true,
      message: 'The password reset email has been sent!',
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: 'Failed to send the password reset email!',
    })
  }
}

/** Reset Password */
const resetPassword = async (req, res, next) => {
  try {
    const {
      newPassword,
      confirmPassword
    } = req.body

    const user = await Users.findAll({
      where: {
        email: req.email
      }
    })
    if (!user) {
      return res.status(404).json({
        success: false,
        message: 'User not found',
      })
    }
    var err = validateNewPassword(req.body)
    if (err) {
      return res.status(400).json({
        success: false,
        message: 'Field must not be empty!'
      })
    }

    if (newPassword !== confirmPassword) {
      return res.status(403).json({
        success: false,
        message: 'Password and confirm password don\'t match!'
      })
    }
    const salt = await bcrypt.genSalt()
    const hashPassword = await bcrypt.hash(newPassword, salt)
    await Users.update({
      password: hashPassword
    }, {
      where: {
        email: req.email
      }
    })
    return res.status(200).json({
      success: true,
      message: 'Password has been successfully updated!',
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
  register,
  login,
  logout,
  generateOTP,
  verifyOTP,
  reSendOTP,
  forgotPassword,
  resetPassword
}