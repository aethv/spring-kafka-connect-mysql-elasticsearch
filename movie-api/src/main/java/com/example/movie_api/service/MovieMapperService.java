package com.example.movie_api.service;

import com.example.movie_api.controller.CreateMovieRequest;
import com.example.movie_api.controller.UpdateMovieRequest;
import com.example.movie_api.model.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieMapperService {

    public Movie toMovie(CreateMovieRequest createMovieRequest) {
        if (createMovieRequest == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setImdbId(createMovieRequest.imdbId());
        movie.setTitle(createMovieRequest.title());
        movie.setYear(createMovieRequest.year());
        movie.setActors(createMovieRequest.actors());
        movie.setPoster(createMovieRequest.poster());
        return movie;
    }

    public void updateMovieFromUpdateMovieRequest(Movie movie, UpdateMovieRequest updateMovieRequest) {
        if (updateMovieRequest == null) {
            return;
        }
        if (updateMovieRequest.title() != null) {
            movie.setTitle(updateMovieRequest.title());
        }
        if (updateMovieRequest.year() != null) {
            movie.setYear(updateMovieRequest.year());
        }
        if (updateMovieRequest.actors() != null) {
            movie.setActors(updateMovieRequest.actors());
        }
        if (updateMovieRequest.poster() != null) {
            movie.setPoster(updateMovieRequest.poster());
        }
    }
}
