package com.example.movie_api.controller;

import com.example.movie_api.model.Movie;
import com.example.movie_api.service.MovieMapperService;
import com.example.movie_api.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapperService movieMapper;

    @GetMapping
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Movie createMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        return movieService.saveMovie(movieMapper.toMovie(createMovieRequest));
    }

    @PatchMapping("/{imdbId}")
    public Movie updateMovie(@PathVariable String imdbId, @RequestBody UpdateMovieRequest updateMovieRequest) {
        Movie movie = movieService.validateAndGetMovie(imdbId);
        movieMapper.updateMovieFromUpdateMovieRequest(movie, updateMovieRequest);
        return movieService.saveMovie(movie);
    }

    @DeleteMapping("/{imdbId}")
    public void deleteMovie(@PathVariable String imdbId) {
        Movie movie = movieService.validateAndGetMovie(imdbId);
        movieService.deleteMovie(movie);
    }
}
