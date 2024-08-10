curl -i -X POST localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -d @connectors/debezium-mysql-moviesdb.json


curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -d @connectors/elasticsearch-sink-movies.json

curl -X PUT localhost:9200/movies \
  -H "Content-Type: application/json" \
  -d @elasticsearch/movies-mapping.json