#! /bin/bash
cd carbon
rm -rf data-1
rm -rf data-2
rm -rf data-3
mkdir data-1
mkdir data-2
mkdir data-3

echo "start mongod in port 27001"
mongod --port 27001 --replSet carbon --dbpath data-1 --bind_ip localhost --oplogSize 128 --wiredTigerJournalCompressor zlib --wiredTigerCollectionBlockCompressor zlib > /dev/null & 
sleep 10
echo "start mongod in port 27002"
mongod --port 27002 --replSet carbon --dbpath data-2 --bind_ip localhost --oplogSize 128 --wiredTigerJournalCompressor zlib --wiredTigerCollectionBlockCompressor zlib > /dev/null &
sleep 10
echo "start mongod in port 27003"
mongod --port 27003 --replSet carbon --dbpath data-3 --bind_ip localhost --oplogSize 128 --wiredTigerJournalCompressor zlib --wiredTigerCollectionBlockCompressor zlib > /dev/null &
sleep 10

mongo --host localhost:27001 repl_set_init.js 
sleep 10
sudo ./replicaset_data.sh

