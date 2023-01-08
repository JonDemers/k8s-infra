#!/bin/bash

set -e

if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <name>"
  exit 1
fi

name=$1

cd $(dirname "$0")/$name

if [ -f "secret.yml" ]; then
  kubectl delete -f secret.yml || true
fi

cat k8s.yml | envsubst | kubectl delete -f -
