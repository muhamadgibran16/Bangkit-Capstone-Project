import numpy as np
import matplotlib.pyplot as plt
import cv2
import pytesseract
import tensorflow as tf
from tensorflow.keras.layers import *
from tensorflow.keras.models import *
from google.cloud import storage
from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from models.userModel import User, db
from gunicorn.app.base import BaseApplication
import os
# import download 

app = Flask(__name__)

# Configuration SQLAlchemy
app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv("DB_CONNECTIONS")
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db.init_app(app)

# Configuration Google Cloud Storage
BUCKET_NAME = 'ember-donor'
BUCKET_FOLDER = 'userprofile'

# Create Google Cloud Storage client using service account JSON file
storage_client = storage.Client()
bucket = storage_client.bucket(BUCKET_NAME)

model = tf.keras.models.load_model("bounding_ktp03.h5")
# pytesseract.pytesseract.tesseract_cmd = r'Tesseract-OCR\\tesseract.exe' # local directory in windows
pytesseract.pytesseract.tesseract_cmd = r'/usr/bin/tesseract' # server directory in linux

@app.route('/', methods=['GET'])
def index():
    return "Gokil Mantul Ngebug Njlimet Nyenyenye"

@app.route('/v1/upload-ktp/<uid>', methods=['PATCH'])
def predict(uid):
    try:
        if 'image' not in request.files:
            return jsonify({'status': 'failure', 'message': 'No file part in the request'}), 400

        file = request.files['image']
        file_path = 'uploaded_ktp.png'
        file.save(file_path)

        blob = bucket.blob(f"{BUCKET_FOLDER}/{uid}/ktp.png")
        blob.upload_from_filename(file_path)

        img = cv2.imread(file_path, 0)
        ret, img = cv2.threshold(img, 150, 255, cv2.THRESH_BINARY_INV)
        img = cv2.resize(img, (512, 512))
        img = np.expand_dims(img, axis=-1)
        img = img / 255

        img = np.expand_dims(img, axis=0)
        pred = model.predict(img)
        pred = np.squeeze(np.squeeze(pred, axis=0), axis=-1)
        plt.imshow(pred, cmap='gray')
        plt.imsave('test_img_mask.png', pred)

        img = cv2.imread('test_img_mask.png', 0)
        cv2.threshold(img, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU, img)
        ori_img = cv2.imread(file_path)
        ori_img = cv2.cvtColor(ori_img, cv2.COLOR_BGR2RGB)
        ori_img = cv2.resize(ori_img, (512, 512))

        roi_img = []

        roi_number = 0

        contours, _ = cv2.findContours(
            img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
        contours = sorted(contours, key=lambda x: cv2.boundingRect(x)[
                        0] + cv2.boundingRect(x)[1] * img.shape[1])

        for c in contours:
            x, y, w, h = cv2.boundingRect(c)

            if w > 50:
                cv2.rectangle(ori_img, (x, y), (x + w, y + h), (36, 255, 12), 2)
                ROI = ori_img[y:y + h, x:x + w]
                roi_img.append(ROI)
                roi_number += 1
                if len(roi_img) > 1:
                    nama = pytesseract.image_to_string(
                        roi_img[0], lang='eng', config='--psm 7')
                    print("Nama: ", nama)
                    jenis_kelamin = pytesseract.image_to_string(
                        roi_img[1], lang='eng', config='--psm 7')
                    print("Jenis Kelamin: ", jenis_kelamin)

                    user = User.query.filter_by(uid=uid).first()

                    if user:
                        user.name = nama
                        user.gender = jenis_kelamin
                        user.ktp = True
                        db.session.commit()
                        return jsonify({'success': 'true', 'message': 'Upload KTP Successfully'}), 200
                    else:
                        return jsonify({'status': 'failure', 'message': 'User not found'}), 404


    except Exception as e:
        return jsonify({'message': str(e)}), 500

class Server(BaseApplication):
    def __init__(self, app, options=None):
        self.options = options or {}
        self.application = app
        super().__init__()

    def load_config(self):
        for key, value in self.options.items():
            self.cfg.set(key, value)

    def load(self):
        return self.application


if __name__ == '__main__':
    # app.run()
    options = {
        'bind': '0.0.0.0:5000',
        'workers': 4 
    }
    server = Server(app, options)
    server.run()
    # download.run()