#!/usr/bin/env bash
echo "going to write the points..."
curl  \
  -H "Accept: application/json" \
  -H "Content-Type:application/json" \
  -X POST --data '{"points": "2432"}'  http://localhost:8080/points/jlong

echo

echo "going to read the points..."
curl http://localhost:8080/points/jlong