from flask import Flask, request, jsonify
from werkzeug.utils import secure_filename
import os
from google.cloud import storage
import pytesseract
from PIL import Image
import re
import numpy as np
from models.userModel import User
from keras.models import load_model
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate

# Set the path to the Tesseract OCR executable
pytesseract.pytesseract.tesseract_cmd = r'Tesseract-OCR\tesseract.exe'

app = Flask(__name__)
UPLOAD_FOLDER = 'uploads/'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

# Configuration for SQLAlchemy
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root:@localhost/db_donor'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)
migrate = Migrate(app, db)

# Configuration for Google Cloud Storage
BUCKET_NAME = 'ember-donor'
BUCKET_FOLDER = 'userprofile'

# Initialize Google Cloud Storage client
storage_client = storage.Client.from_service_account_json('credentials.json')


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


def upload_image_to_bucket(filename):
    bucket = storage_client.bucket(BUCKET_NAME)
    blob = bucket.blob(f'{BUCKET_FOLDER}/{filename}')
    blob.upload_from_filename(os.path.join(UPLOAD_FOLDER, filename))


def preprocess_image(image):
    image = image.resize((512, 512))  # Resize image to (512, 512) pixels
    image = image.crop((0, 0, 512, 512))  # Crop image to (512, 512) pixels
    image = np.array(image)  # Convert PIL Image to NumPy array
    image = image / 255.0  # Normalize pixel values to range [0, 1]
    image = np.expand_dims(image, axis=-1)  # Add channel dimension
    return image


def postprocess_predictions(predictions):
    threshold = 0.5
    bounding_boxes = []
    for i, prediction in enumerate(predictions[0]):
        if np.greater(prediction, threshold).any():
            bounding_box = get_bounding_box(i)  # Function to get the bounding box coordinates
            bounding_boxes.append(bounding_box)
    return bounding_boxes


def extract_text_within_boxes(image, bounding_boxes):
    extracted_text = []
    for box in bounding_boxes:
        left, top, right, bottom = box
        cropped_image = image.crop((left, top, right, bottom))
        text = pytesseract.image_to_string(cropped_image)
        extracted_text.append(text)
    return extracted_text


def process_ktp(filename):
    try:
        image_path = os.path.join(UPLOAD_FOLDER, filename)
        image = Image.open(image_path)

        # Convert image to grayscale
        grayscale_image = image.convert('L')
        processed_image = preprocess_image(grayscale_image)  # Process the image using preprocess_image function

        text = pytesseract.image_to_string(grayscale_image)  # Use grayscale image for OCR

        model = load_model('bounding_ktp03.h5')
        predictions = model.predict(np.expand_dims(processed_image, axis=0))
        bounding_boxes = postprocess_predictions(predictions)
        extracted_text = extract_text_within_boxes(image, bounding_boxes)

        return extracted_text
    except ImportError:
        return 'Tesseract OCR library is not installed'


def extract_data(text_data):
    name_pattern = r'Name:\s+(.*)'
    gender_pattern = r'Gender:\s+(.*)'

    name_match = re.search(name_pattern, text_data)
    gender_match = re.search(gender_pattern, text_data)

    name = name_match.group(1) if name_match else 'Not found'
    gender = gender_match.group(1) if gender_match else 'Not found'

    return name, gender


def update_user_profile(name, gender):
    user_profile = User.query.first()
    user_profile.name = name
    user_profile.gender = gender
    db.session.commit()


def get_bounding_box(index):
    # Dummy implementation, replace with your actual code to retrieve bounding box coordinates
    return (0, 0, 100, 100)  # Example bounding box coordinates


@app.route('/upload-ktp', methods=['POST'])
def upload_ktp():
    if 'file' not in request.files:
        return jsonify({'message': 'No file uploaded'}), 400

    file = request.files['file']
    if not allowed_file(file.filename):
        return jsonify({'message': 'Invalid file extension'}), 400

    file.save(os.path.join(UPLOAD_FOLDER, secure_filename(file.filename)))
    upload_image_to_bucket(file.filename)

    roi_img = []  # List to store region of interest images

    # Process the uploaded image
    image = Image.open(os.path.join(UPLOAD_FOLDER, secure_filename(file.filename)))

    # TODO: Add code to extract region of interest (roi_img) from the image

    if len(roi_img) > 1:
        name = pytesseract.image_to_string(roi_img[0], lang='eng', config='--psm 7')
        print("Nama: ", name)
        gender = pytesseract.image_to_string(roi_img[1], lang='eng', config='--psm 7')
        print("Jenis Kelamin: ", gender)
    elif len(roi_img) == 1:
        text = pytesseract.image_to_string(roi_img[0], lang='eng', config='--psm 7')
        print(text)
    else:
        print("none")

    extracted_text = process_ktp(file.filename)
    name, gender = extract_data(extracted_text)

    update_user_profile(name, gender)

    return jsonify({'name': name, 'gender': gender}), 200


if __name__ == '__main__':
    app.run(debug=True)
