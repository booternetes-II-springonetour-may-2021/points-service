#!/usr/bin/env bash
curl -i \
  -H "Accept: application/json" \
  -H "Content-Type:application/json" \
  -X POST --data '{"points": "2432"}'  http://localhost:8080/points/jlong
