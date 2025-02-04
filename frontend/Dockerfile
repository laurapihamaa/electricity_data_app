# Step 1: Build React application
FROM node:16 AS build

# Set working directory for frontend build
WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the frontend source code
COPY . .

# Build the React app
RUN npm run build

# Step 2: Serve the built React app using Nginx
FROM nginx:alpine

# Copy the built React app from the previous stage
COPY --from=build /app/build /usr/share/nginx/html

# Expose the port
EXPOSE 80

# Default command to run Nginx
CMD ["nginx", "-g", "daemon off;"]
