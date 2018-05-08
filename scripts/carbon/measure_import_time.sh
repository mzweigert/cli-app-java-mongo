#! /bin/bash
touch czas_rpl.csv
chmod 777 czas_rpl.csv
TIME_FILE=czas_rpl.csv
DB_NAME=ctt_2016_data
DATA_FILE=ctt_2016_data.csv.gz
HOSTS=carbon/localhost:27001,localhost:27002,localhost:27003

function import_to_db {

	echo $1
	for(( i=1; i<=5; i++)) do
		/usr/bin/time -f "%Uuser,%Ssystem,%Eelapsed" -o $TIME_FILE -a gunzip -c $DATA_FILE | mongoimport --host $HOSTS --drop -d test -c $DB_NAME --type csv --headerline --writeConcern $1
		sleep 5
	done
}


echo w:1,wtimeout:0 >> $TIME_FILE
import_to_db '{w:1, wtimeout:0}'
echo w:1, j:false >> $TIME_FILE
import_to_db '{w:1,j:false}'
echo w:1, j:true >> $TIME_FILE
import_to_db '{w:1,j:true}'
echo w:2, j:false >> $TIME_FILE
import_to_db '{w:2,j:false}'
echo w:2, j:true >> $TIME_FILE
import_to_db '{w:2,j:true}'

