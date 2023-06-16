import numpy as np
import matplotlib.pyplot as plt
import cv2
import pytesseract
import tensorflow as tf
from tensorflow.keras.layers import *
from tensorflow.keras.models import *

model = tf.keras.models.load_model("bounding_ktp03.h5")
pytesseract.pytesseract.tesseract_cmd = r'C:\\Users\\ASUS\\Downloads\\ML\\Tesseract-OCR\\tesseract.exe'

def predict():
    file_test = 'sample_ktp.png'
    img = cv2.imread(file_test, 0)
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
    ori_img = cv2.imread(file_test)
    ori_img = cv2.cvtColor(ori_img, cv2.COLOR_BGR2RGB)
    ori_img = cv2.resize(ori_img, (512, 512))

    contours, hier = cv2.findContours(img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
    contours = sorted(contours, key=lambda x: cv2.boundingRect(x)[0] + cv2.boundingRect(x)[1] * img.shape[1])

    for c in contours:
        x, y, w, h = cv2.boundingRect(c)
        
        if w > 50:
            cv2.rectangle(ori_img, (x, y), (x + w, y + h), (36, 255, 12), 2)
            ROI = ori_img[y:y + h, x:x + w]
            text = pytesseract.image_to_string(ROI)
            print("Text:", text)

    plt.imshow(ori_img)
    plt.show()

predict()
