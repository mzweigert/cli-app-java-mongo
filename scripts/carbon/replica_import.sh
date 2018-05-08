#! /bin/bash
DB_NAME=ctt_2016_data
DATA_FILE=ctt_2016_data.csv.gz
HOSTS=carbon/localhost:27001,localhost:27002,localhost:27003

gunzip -c $DATA_FILE | mongoimport --host $HOSTS --drop -d test -c $DB_NAME --type csv --headerline --writeConcern '{w:1,j:false}'
