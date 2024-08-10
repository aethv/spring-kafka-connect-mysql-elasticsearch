package com.example.movie_search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.movie_search.model.Movie;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private ElasticsearchClient client;

    @Value("${elasticsearch.indexes.movies}")
    private String moviesIndex;

    public List<Movie> searchMovies() {
        return searchMovies(
                SearchRequest.of(
                        searchRequestBuilder -> searchRequestBuilder.index(moviesIndex)));
    }

    public List<Movie> searchMovies(String title) {
        return searchMovies(
                SearchRequest.of(
                        searchRequestBuilder -> searchRequestBuilder
                                .index(moviesIndex)
                                .query(queryBuilder -> queryBuilder
                                        .term(termQueryBuilder -> termQueryBuilder
                                                .field("title").value(title)))));
    }

    private List<Movie> searchMovies(SearchRequest searchRequest) {
        try {
            SearchResponse<Movie> searchResponse = client.search(searchRequest, Movie.class);
            List<Hit<Movie>> hits = searchResponse.hits().hits();
            return hits.stream().map(Hit::source).toList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "An exception occurred while searching movies. %s".formatted(e.getMessage()));
        }
    }
}
