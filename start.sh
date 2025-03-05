#!/bin/bash

# Start the backend
echo "Starting the backend..."
cd backend
mvn spring-boot:run &
BACKEND_PID=$!

# Wait for the backend to start
echo "Waiting for the backend to start..."
sleep 10

# Start the frontend
echo "Starting the frontend..."
cd ../frontend
npm run dev &
FRONTEND_PID=$!

# Function to handle script termination
function cleanup {
  echo "Stopping services..."
  kill $FRONTEND_PID
  kill $BACKEND_PID
  exit
}

# Trap SIGINT (Ctrl+C) and call cleanup
trap cleanup SIGINT

# Keep the script running
echo "Both services are running. Press Ctrl+C to stop."
wait 