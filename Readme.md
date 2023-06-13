# **Machine Learning**

Machine learning (ML) is defined as a discipline of artificial intelligence (AI) that provides machines the ability to automatically learn from data and past experiences to identify patterns and make predictions with minimal human intervention. In this Machine Learning section, we make OCR for KTP and this function is to validate the user in our application. And for the model itself we put it in our Drive because most of our model that we made have a large size.

Google Drive for model : https://drive.google.com/drive/folders/1wSpkMhaKiAB5KmO0xKis2JB4q0tftvNn?usp=sharing

# **OCR KTP**

OCR stands for Optical Character Recognition, which is a technology used in machine learning to automatically recognize and extract text from images or scanned documents. It involves converting the text contained in these visual representations into machine-readable and editable formats. For OCR KTP method we divide it in 2 ways : 

1. We developed a special program using machine learning called a Convolutional Neural Network (CNN). Its purpose is to identify and classify characters ranging from 0 to 9 and A to Z. To train the program, we collected a dataset of images containing these characters. These images served as examples for the program to learn from. To help the program focus on the characters, we utilized OpenCV, a library that aided in locating and separating the characters within the images. This allowed us to create bounding boxes around each character for individual processing. After isolating the characters, we saved them as separate images, which became the training data for our CNN model. Following the training phase, the program gained the ability to predict the characters present in new images. By inputting unseen images, the program can now determine which characters it recognizes.

Standard OCR Dataset : https://www.kaggle.com/datasets/preatcher/standard-ocr-dataset

2. We built a segmentation model using the U-Net architecture to separate objects in images. We created our own dataset and manually labeled the objects of interest. After training the model, we used it to segment the text regions in new images. By finding the contours of these regions using OpenCV, we created bounding boxes around them. Next, we cropped and saved each bounding box as a separate image. To extract the text from these images, we used pytesseract, a library specialized in text recognition from images. With pytesseract, we were able to accurately extract the text from the cropped images. This technology automates the extraction of text from images, making it useful for tasks like document processing and text recognition in various applications.




## **Dataset**
These are our Datasets : 
1. Standard OCR Dataset : https://www.kaggle.com/datasets/preatcher/standard-ocr-dataset
2. Handwritten Dataset : https://www.kaggle.com/datasets/sachinpatel21/az-handwritten-alphabets-in-csv-format

And we create our own dataset for KTP and masking segmentation using Figma 
Link to Dataset : https://www.figma.com/file/M638IqpM5D7Zy2UQAmh6qZ/DATASET-KTP?type=design&node-id=1%3A8516&t=gR1mYSlItcnVtT4p-1
