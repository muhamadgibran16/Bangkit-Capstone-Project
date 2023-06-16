const jwt = require('jsonwebtoken')
const {
  Users
} = require('../../auth-api/models/userModel')

const refreshToken = async (req, res) => {
  try {
    console.log('cookie => ', req.cookies)
    const refreshToken = req.cookies.refreshToken
    if (!refreshToken) {
      return res.status(401).json({
        success: false,
        message: 'Unauthorized'
      })
    }
    console.log('user => ', Users)
    const user = await Users.findOne({
      where: {
        refresh_token: refreshToken
      },
    })
    if (!user) {
      return res.status(403).json({
        success: false,
        message: 'Forbidden'
      })
    }
    jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET_KEY, (err, decoded) => {
      if (err) {
        return res.status(403).json({
          success: false,
          message: 'Forbidden'
        })
      }
      const uid = user.uid
      const name = user.name
      const email = user.email
      const accessToken = jwt.sign({
          uid,
          name,
          email
        },
        process.env.ACCESS_TOKEN_SECRET_KEY, {
          expiresIn: '24h'
        })
      res.status(200).json({
        success: true,
        payload: {
          token: accessToken
        }
      })
    })
  } catch (err) {
    console.log(err)
    res.status(404).json({
      success: false,
      message: err.message
    })
  }
}

module.exports = {
  refreshToken
}