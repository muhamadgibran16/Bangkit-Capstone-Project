# Cloud Computing

Cloud computing is a computational model that utilizes the internet to provide flexible, scalable, and managed computing resources. In the project of blood donation application, cloud computing plays a vital role in various aspects, including creating a RESTful API, deploying the backend application and database, and deploying machine learning models.

By leveraging cloud computing, the project can benefit from the following advantages. Firstly, creating a RESTful API allows for seamless communication between the frontend and backend components of the application, enabling efficient data transfer and interaction. Secondly, deploying the backend application and database to the cloud enables easy scalability and high availability, ensuring that the application can handle increasing user traffic and data storage demands. Lastly, deploying machine learning models on the cloud allows for efficient and scalable processing of data, enabling real-time predictions and analysis.

Overall, cloud computing empowers the blood donation application project by providing a reliable and flexible infrastructure to develop, deploy, and manage the various components of the application, ultimately enhancing its functionality and performance.

## API Documentation

Link to documentation : [API Documentation https://documenter.getpostman.com/view/27517500/2s93sXcukE](https://documenter.getpostman.com/view/27517500/2s93sXcukE)

## RESTfull API

A RESTful API is an interface used in application development to facilitate efficient communication and data exchange between different systems. By leveraging the principles of REST (Representational State Transfer), this API provides callable methods to access, manipulate, and retrieve data over the HTTP protocol. In this project, our role is to provide an API that can be used by the Mobile Development team. The APIs we create include Authentication and Authorization, OTP Generation sent via email, Location API, and APIs for the main features of the application.

By implementing a RESTful API, we enable seamless integration between the mobile application and the backend systems. The Authentication and Authorization API ensures secure access to the application, allowing only authorized users to perform certain actions. The OTP Generation API generates one-time passwords that are sent to users via email for additional security. The Location API provides functionality to retrieve and update location data, enabling features such as finding nearby blood donation centers. Additionally, we create APIs for the core features of the application, allowing the mobile development team to efficiently retrieve and manipulate data required for the application's functionality.

By providing these APIs, we enable the Mobile Development team to effectively communicate with the backend systems, retrieve necessary data, and perform required actions, ensuring a seamless and efficient user experience in the blood donation application.

### Authentication and Authorization

In the Authentication API, we have implemented several methods and features. Some of them are:

- User Registration: Allows users to create a new account by providing necessary information such as name, email address, phone number, and password.

- User Authentication: Validates the user's identity using valid credentials such as their email and password, access tokens, or other authentication methods.

- Access Token Generation: After successful authentication, the API generates an access token that serves as an authorization key to access protected API resources.

- Access Token Usage/Endpoint Protection: Protects API endpoints that require authentication by checking the presence and validity of the access token provided by the user in each request. Users must include the access token in every request to authenticated API endpoints. The token is verified to ensure that the user has the appropriate permissions to access the requested resources.

- Refresh Token: When the access token expires, the API can provide a refresh token feature. The refresh token is used to obtain or renew a new access token without requiring the user to re-enter their credentials.
  
- OTP (One Time Password): Users are prompted to enter the OTP they receive via email after entering their login or registration credentials, such as username and password. OTP serves as an additional security layer to ensure that the individual attempting to access the account is the legitimate owner.
  
- Logout: Provides a mechanism to end the user's session and invalidate the associated access token, preventing the user from accessing protected resources after logging out.

- Password Recovery: Offers a way for users who have forgotten their password to regain access to their account through methods like sending a recovery link via email.

These features and methods provide a comprehensive authentication system for the API, ensuring secure access to user accounts and protected resources while offering mechanisms for user registration, login, token management, and password recovery.

### Location API

The Location API is a programming application interface (API) that provides geographical location information such as geographic coordinates (latitude and longitude), addresses, hospital names, and more. By utilizing the Location API, we can integrate location functionality into the application to display data. The location data obtained from this API can be used for various purposes, such as displaying information around a location to show a list of patients in need of blood who are currently admitted to hospitals.

### Donor API

Donor App is an application designed to facilitate the process of collecting and managing blood donor data. This application provides a user-friendly interface for individuals to register as blood donors, search for nearby blood donation schedules and locations, and access important information about blood donation.

By using Donor App, users can perform several key functions, including:

- Donor Registration: Users can register as volunteer blood donors or request blood donations by providing their personal information.

- Blood Donation Schedule Search: The application offers a feature to search for blood donation schedules based on location or specific dates. Users can view a list of available donation schedules and choose the most suitable time and location.

- Donor History: The application keeps a record of users' blood donation history, including the date of their last donation and blood type.

- Donor Information and Blood Needs: Donor App provides information about blood donation, such as donor requirements, the benefits of blood donation, and blood needs at various locations.

With the presence of Donor App, it is expected that the process of collecting and managing blood donor data will be more efficient and organized. The application also plays a role in raising public awareness about the importance of blood donation and helping ensure an adequate blood supply for medical purposes.

## Deploy API to Cloud Run using GitHub Actions

- Prepare a GitHub repository for the API project.

- Configure the API deployment settings using a Dockerfile, including specifying the required dependencies, source code, and runtime environment:
```
# Use Node.js base image version 16
FROM node:16-slim

# Set the working directory in the container
WORKDIR /app

# Copy the entire content from the local directory
ADD . /app

# Copy package.json and package-lock.json files
COPY package*.json ./

# Install Dependencies
RUN npm install

# Copy the application source code
COPY . .

# Specify the port
EXPOSE 8080

# Run the application
CMD ["node", "app.js"]
```
- Create a repository secret for environment variables such as project ID, service account, hostname, and other sensitive data.
   
- Create a GitHub Actions workflow file named ```.github/workflows/main.yml``` to automate the deployment process.
   
Specify the necessary steps to build and deploy the API to Cloud Run and set up the required authentication and access permissions for the GitHub Actions workflow to interact with Cloud Run:
- Define the workflow trigger condition ```on```.
- Define the jobs to be executed ```jobs```.
- Specify the operating system to be used ```runs-on: ubuntu-latest```
- Set environment variables.
- Perform Google Cloud login ```google-github-actions/setup-gcloud```
- Configure Docker authorization ```gcloud auth configure-docker --quiet```
- Checkout the Repository ```actions/checkout```
- Build the Docker Image ```docker build -t $IMAGE_NAME .```
- Push the image to the Container Registry ```docker push $IMAGE_NAME```
- Deploy the Image to Cloud Run ```gcloud run deploy```

Push the changes to the GitHub repository to trigger the workflow and initiate the deployment process.


Noted : [Backend Server Running on : https://github.com/muhamadgibran16/donor-api](https://github.com/muhamadgibran16/donor-api)



## Deployment OCR API - Machine Learning Model

This endpoint is used to receive input in the form of an ID card image, then process it with a model, and return the output as text, which includes the name and gender data. The data is then saved in a database, and the ID card image is stored in a non-public cloud storage.

Deployment process:

- Prepare a GitHub repository for the API project.

- Configure the API deployment settings using a Dockerfile, including specifying the required dependencies, source code, and runtime environment:
```
# Base image
FROM python:3.10-slim

# Set working directory
WORKDIR /app

RUN pip install --upgrade pip

# Install build tools
RUN apt-get update && apt-get install -y build-essential

# Install system-level dependencies
RUN apt-get update && apt-get install -y default-libmysqlclient-dev libgl1-mesa-glx tesseract-ocr

RUN apt-get install -y tesseract-ocr-eng

# Copy requirements.txt
COPY requirements.txt .

# Install dependencies
RUN pip install -r requirements.txt

# Copy application files
COPY . .

# Expose the application port
EXPOSE 5000

# Set environment variables
ENV PYTHONUNBUFFERED=1

# Run the application
CMD ["gunicorn", "app:app", "--bind", "0.0.0.0:5000"]
```
- Create a repository secret for environment variables such as project ID, service account, database configuratiion, and other sensitive data.
   
- Create a GitHub Actions workflow file named ```.github/workflows/main.yml``` to automate the deployment process.
   
Specify the necessary steps to build and deploy the API to Cloud Run and set up the required authentication and access permissions for the GitHub Actions workflow to interact with Cloud Run:
- Define the workflow trigger condition ```on```.
- Define the jobs to be executed ```jobs```.
- Specify the operating system to be used ```runs-on: ubuntu-latest```
- Set environment variables.
- Perform Google Cloud login ```google-github-actions/setup-gcloud```
- Configure Docker authorization ```gcloud auth configure-docker --quiet```
- Checkout the Repository ```actions/checkout```
- Build the Docker Image ```docker build -t $IMAGE_NAME .```
- Push the image to the Container Registry ```docker push $IMAGE_NAME```
- Deploy the Image to Cloud Run ```gcloud run deploy```

Push the changes to the GitHub repository to trigger the workflow and initiate the deployment process.

By following this deployment process, the endpoint can efficiently receive an ID card image, extract the required data, securely store the image in a non-public cloud storage, and provide the extracted information as output while maintaining data privacy and security.

Noted : [ocr model running on : https://github.com/muhamadgibran16/deploy-ocr-model](https://github.com/muhamadgibran16/deploy-ocr-model)
