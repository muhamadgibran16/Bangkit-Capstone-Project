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
