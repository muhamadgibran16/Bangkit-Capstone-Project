const {
  Storage
} = require('@google-cloud/storage')
const Multer = require('multer')
const util = require('util')

const multer = Multer({
  storage: Multer.memoryStorage(),
  limits: {
    fileSize: 5 * 1024 * 1024,
  },
}).single('image')

/** Configuration to Google Cloud Storage */ 
const storage = new Storage({
  keyFilename: process.env.GOOGLE_APPLICATION_CREDENTIALS,
  projectId: process.env.GCP_PROJECT_ID,
})

const bucket = storage.bucket('ember-donor')

/** Processing file upload */
const processFiles = util.promisify(multer)

module.exports = {
  processFiles
}