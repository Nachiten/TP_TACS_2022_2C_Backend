#! /bin/bash
docker volume create mongodbdata
docker run \
    -p 27017:27017 \
    -v mongodbdata:/data/db \
	-e MONGO_INITDB_ROOT_USERNAME=root \
	-e MONGO_INITDB_ROOT_PASSWORD=example \
	mongo