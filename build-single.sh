#!/bin/bash

set -e

if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <name>"
  exit 1
fi

name=$1

cd $(dirname "$0")/$name

if [ -f "Dockerfile" ]; then
  docker_name=com.opcode.k8s-infra.$name
  docker build --tag $docker_name .
  export DOCKER_IMAGE=docker-registry.k8s.lan:30501/$docker_name:latest-$(date "+%Y.%m.%d-%H.%M.%S")
#  docker login -u $DOCKER_CREDENTIAL_USR -p $DOCKER_CREDENTIAL_PSW http://debian-cloud-test:8083
  docker tag $docker_name $DOCKER_IMAGE
  docker push $DOCKER_IMAGE
fi

../delete-single.sh "$name"

if [ -d "config-map-from-file" ]; then
  cd config-map-from-file
  for file in *; do
    kubectl create configmap $name-$file-config-map --from-file=$file
  done
  cd ..
fi

if [ -f "secret.yml" ]; then
  kubectl apply -f secret.yml
fi

cat k8s.yml | envsubst | kubectl apply -f -
