package com.example.movie_search.controller;

import com.example.movie_search.model.Movie;
import com.example.movie_search.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/")
    public String getHome() {
        return "redirect:/movies";
    }

    @GetMapping("/movies")
    public String getMovies(Model model) {
        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("movies", movieService.searchMovies());
        return "movies";
    }

    @PostMapping("/movies/search")
    public String searchMovies(@ModelAttribute SearchRequest searchRequest,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (!StringUtils.hasText(searchRequest.getText())) {
            return "redirect:/movies";
        }
        List<Movie> movies = movieService.searchMovies(searchRequest.getText());
        if (movies.isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    "No movies with title containing '%s' were found!".formatted(searchRequest.getText()));
            return "redirect:/movies";
        }
        model.addAttribute("movies", movies);
        return "movies";
    }
}
