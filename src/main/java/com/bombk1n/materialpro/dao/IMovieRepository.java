package com.bombk1n.materialpro.dao;

import com.bombk1n.materialpro.model.MovieModel;

import java.util.List;
import java.util.Optional;

public interface IMovieRepository<T extends MovieModel> {
    T saveMovie(T movie);
    List<T> getAllMovies();
    Optional<T> getMovie(String id);
    T updateMovie(String id, T updatedMovie);
    void deleteMovie(String id);
    boolean existsMovie(String id);
    List<T> saveAll(List<T> movies);
}
