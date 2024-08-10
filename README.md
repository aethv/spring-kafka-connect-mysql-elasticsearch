Demostration of the project
```Spring Boot - Kafka + MySQL Connector + ElasticSearch```

## Steps
1. Start Docker Compose: 
```bash
docker-compose up -d
```

2. Bootstrap sink connector:
```bash
curl -i -X POST localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -d @connectors/debezium-mysql-moviesdb.json

curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -d @connectors/elasticsearch-sink-movies.json
```
Confirm connector at: Kafka Connect UI at http://localhost:8086

3. Update ElasticSearch Mapping
```bash
curl -X PUT localhost:9200/movies \
  -H "Content-Type: application/json" \
  -d @elasticsearch/movies-mapping.json
```

4. MySQL Data Insertion
```bash
docker exec -it -e MYSQL_PWD=secret mysql mysql -uroot --database moviesdb
```
```sql
INSERT INTO `movies` (`imdb_id`, `year`, `title`, `actors`, `poster`)
    VALUES (
      'tt7286456',
      1019,
      'Joker',
      'Joaquin Phoenix, Robert De Niro, Zazie Beetz',
      'https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg'
  );
```
Confirm data synced by Kafdrop (at http://localhost:9000)

5. Start movie-api and movie-search Spring Boot Application
```bash
./mvnw clean spring-boot:run
```

6. Head up http://localhost:8080 for Movie UI page
7. Inserting to MySQL and confirm with (6)
```bash
curl -i -X POST localhost:9080/api/movies \
   -H 'Content-Type: application/json' \
   -d '{"imdbId": "tt0163651", "title": "American Pie", "year": 1999, "actors": "Jason Biggs, Chris Klein, Thomas Ian Nicholas", "poster": "https://m.media-amazon.com/images/M/MV5BMTg3ODY5ODI1NF5BMl5BanBnXkFtZTgwMTkxNTYxMTE@._V1_SX300.jpg"}'

curl -i -X POST localhost:9080/api/movies \
   -H 'Content-Type: application/json' \
   -d '{"imdbId": "tt0252866", "title": "American Pie 2", "year": 2001, "actors": "Jason Biggs, Seann William Scott, Shannon Elizabeth", "poster": "https://m.media-amazon.com/images/M/MV5BOTEyYjhiMjYtNjU3YS00NmQ4LTlhNTEtYTczNWY3MGJmNzE2XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"}'

curl -i -X POST localhost:9080/api/movies \
   -H 'Content-Type: application/json' \
   -d '{"imdbId": "tt0169547", "title": "American Beauty", "year": 1999, "actors": "Kevin Spacey, Annette Bening, Thora Birch", "poster": "https://m.media-amazon.com/images/M/MV5BNTBmZWJkNjctNDhiNC00MGE2LWEwOTctZTk5OGVhMWMyNmVhXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg"}'

curl -i -X POST localhost:9080/api/movies \
   -H 'Content-Type: application/json' \
   -d '{"imdbId": "tt0075148", "title": "Rocky", "year": 1976, "actors": "Sylvester Stallone, Talia Shire, Burt Young", "poster": "https://m.media-amazon.com/images/M/MV5BNTBkMjg2MjYtYTZjOS00ODQ0LTg0MDEtM2FiNmJmOGU1NGEwXkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_SX300.jpg"}'
```
