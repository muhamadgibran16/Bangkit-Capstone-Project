# Base image
FROM python:3.10-slim

# Set working directory
WORKDIR /app

ADD https://storage.googleapis.com/ember-donor/model/bounding_ktp03.h5 /app

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
