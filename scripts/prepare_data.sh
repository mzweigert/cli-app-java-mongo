#! /bin/bash
mkdir -p carbon
cd carbon

curl -L "https://transfer.sh/11psru/chicago_taxi_trips_2016.csv" > ctt_2016_data.csv
gzip ctt_2016_data.csv
