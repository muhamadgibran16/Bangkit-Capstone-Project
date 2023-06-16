const express = require('express')
const router = express.Router()
const {
  verifyToken,
  verifyUser,
} = require('../../auth-api/middleware/auth')
const {
  processFiles
} = require('../../auth-api/middleware/users')
const {
  refreshToken,
} = require('../../auth-api/handlers/refreshToken')
const users = require('../../auth-api/handlers/users')
const authenticate = require('../handlers/auth')


/** Authenticate */
router.post('/register', authenticate.register)
router.post('/authenticate', verifyUser, (req, res) => res.end())
router.post('/login', authenticate.login)
router.post('/verify-otp', authenticate.verifyOTP)
router.post('/send-otp', authenticate.reSendOTP)

router.get('/token', refreshToken)

router.post('/forgot-password', verifyUser, authenticate.forgotPassword)
router.patch('/reset-password/:accessToken', verifyToken, authenticate.resetPassword)

router.delete('/logout', authenticate.logout)

/** Users */
router.get('/users', verifyToken, users.getUsers)

router.patch('/img-upload', verifyToken, processFiles, users.imgUpload)
router.patch('/update-profile', verifyToken, processFiles, users.updateProfile)
router.patch('/update-dialog', verifyToken, users.updateDialogFirst)
router.patch('/upload-ktp', verifyToken, processFiles, users.uploadKTP)


module.exports = router