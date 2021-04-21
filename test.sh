#!/usr/bin/env bash

def_host=localhost:8080
my_host=${1:-${def_host}}

echo  the host is "$my_host"


echo "going to read the points again..."
curl http://${my_host}/points/jlong

echo "going to write the points..."
curl \
  -H "Accept: application/json" \
  -H "Content-Type:application/json" \
  -X POST --data '{"points": "2"}' http://${my_host}/points/jlong
#
#echo
#
#echo "going to read the points again..."
#curl http://${my_host}/points/jlong
#
#
#
#
#echo "going to write the points..."
#curl \
#  -H "Accept: application/json" \
#  -H "Content-Type:application/json" \
#  -X POST --data '{"points": "4"}' http://${my_host}/points/jlong
#
#echo
#
#echo "going to read the points again..."
#curl http://${my_host}/points/jlong
