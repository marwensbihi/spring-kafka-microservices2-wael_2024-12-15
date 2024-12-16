@echo off

:: Set Elasticsearch credentials
set USERNAME=elastic
set PASSWORD=adem
set URL=localhost:9200

:: Create the product_index
curl -u %USERNAME%:%PASSWORD% -X PUT "%URL%/product_index"

:: Create the transaction_index
curl -u %USERNAME%:%PASSWORD% -X PUT "%URL%/transaction_index"

:: List indices
curl -u %USERNAME%:%PASSWORD% -X GET "%URL%/_cat/indices?v"

