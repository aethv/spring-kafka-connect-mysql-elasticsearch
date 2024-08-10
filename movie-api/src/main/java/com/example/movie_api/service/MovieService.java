package com.example.movie_api.service;

import com.example.movie_api.model.Movie;
import com.example.movie_api.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie validateAndGetMovie(String imdbId) {
        return movieRepository.findById(imdbId).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }
}
