#!/bin/bash

set -e

if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <name>"
  exit 1
fi

name=$1
docker_name=com.opcode.k8s-infra.$name

cd $(dirname "$0")/$name

docker build --tag $docker_name .

export DOCKER_IMAGE=debian-cloud-test:8083/$docker_name:latest-$(date "+%Y.%m.%d-%H.%M.%S")

docker login -u $DOCKER_CREDENTIAL_USR -p $DOCKER_CREDENTIAL_PSW http://debian-cloud-test:8083

docker tag $docker_name $DOCKER_IMAGE

docker push $DOCKER_IMAGE

kubectl apply -f secret.yml

cat k8s.yml | envsubst | kubectl apply -f -
