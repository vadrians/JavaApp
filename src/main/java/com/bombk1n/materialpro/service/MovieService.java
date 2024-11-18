package com.bombk1n.materialpro.service;

import com.bombk1n.materialpro.dao.MovieRepository;
import com.bombk1n.materialpro.dto.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        return movieRepository.findById(id).map(movie -> {
            if (updatedMovie.getTitle() != null) {
                movie.setTitle(updatedMovie.getTitle());
            }
            if (updatedMovie.getDirector() != null) {
                movie.setDirector(updatedMovie.getDirector());
            }
            if (updatedMovie.getReleaseYear() > 0) {
                movie.setReleaseYear(updatedMovie.getReleaseYear());
            }
            if (updatedMovie.getGenre() != null) {
                movie.setGenre(updatedMovie.getGenre());
            }
            if (updatedMovie.getDuration() > 0) {
                movie.setDuration(updatedMovie.getDuration());
            }
            if (updatedMovie.getRating() > 0) {
                movie.setRating(updatedMovie.getRating());
            }
            if (updatedMovie.getCoverImage() != null) {
                movie.setCoverImage(updatedMovie.getCoverImage());
            }
            if (updatedMovie.getActors() != null && !updatedMovie.getActors().isEmpty()) {
                movie.setActors(updatedMovie.getActors());
            }
            if (updatedMovie.getShowtimes() != null && !updatedMovie.getShowtimes().isEmpty()) {
                movie.setShowtimes(updatedMovie.getShowtimes());
            }
            return movieRepository.save(movie);
        }).orElseThrow(() -> new RuntimeException("Movie not found with id " + id));
    }

    public void deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        } else {
            throw new RuntimeException("Movie not found with id " + id);
        }
    }

    public List<Movie> saveMovies(List<Movie> movies) {
        return movieRepository.saveAll(movies);
    }
}
