package com.example.movie_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    private String imdbId;

    private String title;
    private Integer year;
    private String actors;
    private String poster;
}
