package com.example.movie_search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Movie(
        @JsonProperty("imdb_id") String imdbId,
        String title,
        Integer year,
        String actors,
        String poster,
        @JsonProperty("created_at") Long createdAt,
        @JsonProperty("updated_at") Long updatedAt) {
}
