#!/bin/bash
NAMESPACE=auth-poc
IMAGE_ID=$(docker images -q ${NAMESPACE})
SCRIPT_DIR="$( cd "$(dirname "$0")" ; pwd -P )"

if [ -z ${IMAGE_ID} ]
then
  echo "did not find an existing ${NAMESPACE} image, building one..."
  docker build -t ${NAMESPACE} .
fi

DOCKER_ENV=""
for VAR in $(printenv | egrep -e '^AWS_'); do
  DOCKER_ENV="-e ${VAR} ${DOCKER_ENV}"
done

echo "Shell out to the provision docker container" 
docker run ${DOCKER_ENV} -it \
  -v "${SCRIPT_DIR}":/${NAMESPACE} \
  -v "${HOME}/.aws":/root/.aws \
  ${NAMESPACE} "$@"