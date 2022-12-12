#!/bin/bash

set -e

cd $(dirname "$0")

#  webtop \
for infra in \
  apache \
  k8s \
;
do
  ./build-single.sh $infra
done
