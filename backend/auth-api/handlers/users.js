const {
  Storage
} = require('@google-cloud/storage')
const {
  Users
} = require('../../auth-api/models/userModel')

/** Configuration to Google Cloud Storage */
const storage = new Storage({
  keyFilename: process.env.GOOGLE_APPLICATION_CREDENTIALS,
  projectId: process.env.GCP_PROJECT_ID,
})

/** Set up bucket name */
const bucket = storage.bucket('ember-donor')

/** Upload Photo Profile */
const imgUpload = async (req, res) => {
  try {
    if (!req.file) {
      return res.status(400).json({
        message: 'Please upload a file!',
      })
    }
    const folder = 'userprofile'
    const filename = `${folder}/${req.uid}/${req.file.originalname}`
    const blob = bucket.file(filename)
    const blobStream = blob.createWriteStream()

    blobStream.on('error', (err) => {
      res.status(500).json({
        message: err.message,
      })
    })
    const publicUrl = new URL(`https://storage.googleapis.com/${bucket.name}/${blob.name}`)
    blobStream.on('finish', async () => {
      await blob.makePublic()
      try {
        console.log('User => ', Users)
        await Users.update({
          photo: publicUrl.toString()
        }, {
          where: {
            uid: req.uid
          }
        })
        res.status(200).json({
          success: true,
          message: 'File uploaded successfully and URL is inserted into the database!',
          image: filename,
          url: publicUrl,
        })
      } catch (err) {
        console.log(err)
        res.status(500).json({
          message: 'File uploaded successfully, but URL is not inserted into the database!',
          image: filename,
          url: publicUrl,
        })
      }
    })
    blobStream.end(req.file.buffer)
  } catch (err) {
    console.log(err)
    res.status(500).send({
      message: `Could not upload the file. ${err}`,
    })
  }
}

/** Edit Profile */
const updateProfile = async (req, res, next) => {
  try {
    const {
      telp,
      nik,
      alamat,
      ttl,
      gol_darah,
      rhesus,
      last_donor,
      gender,
    } = req.body

    await Users.update({
      telp: telp,
      nik: nik,
      alamat: alamat,
      ttl: ttl,
      gol_darah: gol_darah,
      rhesus: rhesus,
      last_donor: last_donor,
      gender: gender,
    }, {
      where: {
        uid: req.uid
      }
    })
    res.status(201).json({
      success: true,
      message: 'Data updated successfully!',
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message
    })
  }
}

/** Update Dialog: Blood Types, Rhesus and Last Donor */
const updateDialogFirst = async (req, res, next) => {
  try {
    const {
      gol_darah,
      rhesus,
      last_donor,
      gender,
    } = req.body

    await Users.update({
      gol_darah: gol_darah,
      rhesus: rhesus,
      last_donor: last_donor,
      gender: gender
    }, {
      where: {
        uid: req.uid
      }
    })
    res.status(201).json({
      success: true,
      message: 'Data updated successfully!',
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: err.message
    })
  }
}

/** Get Users Data */
const getUsers = async (req, res, next) => {
  try {
    const users = await Users.findAll({
      attributes: ['uid', 'name', 'email', 'telp', 'nik', 'ttl', 'alamat', 'gol_darah', 'rhesus', 'gender', 'last_donor', 'photo', 'verified', 'ktp'],
      where: {
        email: req.email
      },
    })
    console.log("data => ", users)
    res.status(200).json({
      success: true,
      payload: users,
    })
  } catch (err) {
    console.log(err)
    res.status(500).json({
      success: false,
      message: 'Failed to retrieve user data!',
    })
  }
}


/** Upload Photo KTP: Skenario terburuk jika ML failed di processing*/
const uploadKTP = async (req, res) => {

  if (!req.file) {
    return res.status(400).json({
      message: 'Please upload a file!',
    })
  }
  const folder = 'userprofile'
  const filename = `${folder}/${req.uid}/${req.file.originalname}`
  const blob = bucket.file(filename)
  const blobStream = blob.createWriteStream()

  try {
    blobStream.on('error', (err) => {
      res.status(500).json({
        message: err.message,
      })
    })
    blobStream.on('finish', async () => {
      const expirationDate = new Date()
      expirationDate.setFullYear(expirationDate.getFullYear() + 5)

      const config = {
        action: 'read',
        expires: expirationDate,
      };

      const [privateUrl] = await blob.getSignedUrl(config)

      res.status(200).json({
        success: true,
        message: 'Upload KTP successfully!',
        image: filename,
        url: privateUrl,
      })
    })
    blobStream.end(req.file.buffer)
  } catch (err) {
    console.log(err)
    res.status(500).send({
      message: `Could not upload the file. ${err}`,
    })
  }
}

module.exports = {
  getUsers,
  imgUpload,
  updateDialogFirst,
  updateProfile,
  uploadKTP,
}