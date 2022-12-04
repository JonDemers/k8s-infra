#!/bin/bash

set -e

cd $(dirname "$0")

for infra in \
  apache \
;
do
  ./build-single.sh $infra
done
