package com.bombk1n.materialpro.dao;

import com.bombk1n.materialpro.model.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface IMovieRepository{
    MovieEntity saveMovie(MovieEntity movie);
    List<MovieEntity> getAllMovies();
    Optional<MovieEntity> getMovie(String id);
    MovieEntity updateMovie(String id, MovieEntity updatedMovie);
    void deleteMovie(String id);
    boolean existsMovie(String id);
    List<MovieEntity> saveAll(List<MovieEntity> movies);
}
