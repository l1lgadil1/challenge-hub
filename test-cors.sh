#!/bin/bash

echo "Testing CORS configuration..."

# Test preflight request
echo "Testing preflight request..."
curl -X OPTIONS \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: GET" \
  -H "Access-Control-Request-Headers: Content-Type, Authorization" \
  -v \
  http://localhost:8080/api/challenges

echo ""
echo "Testing actual request..."
# Test actual request
curl -X GET \
  -H "Origin: http://localhost:3000" \
  -H "Content-Type: application/json" \
  -v \
  http://localhost:8080/api/challenges

echo ""
echo "CORS test completed." 