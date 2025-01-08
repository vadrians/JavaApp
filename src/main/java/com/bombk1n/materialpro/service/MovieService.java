package com.bombk1n.materialpro.service;

import com.bombk1n.materialpro.dao.IMovieRepository;
import com.bombk1n.materialpro.dto.MovieDTO;
import com.bombk1n.materialpro.exceptions.DatabaseConnectionException;
import com.bombk1n.materialpro.exceptions.MovieNotFoundException;
import com.bombk1n.materialpro.model.MovieDocument;
import com.bombk1n.materialpro.model.MovieModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final IMovieRepository<MovieDocument> movieMongoDbRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieService(
            @Qualifier("movieMongoDbRepository") IMovieRepository<MovieDocument> movieMongoDbRepository,
            ModelMapper modelMapper) {
        this.movieMongoDbRepository = movieMongoDbRepository;
        this.modelMapper = modelMapper;
    }


    public MovieDTO saveMovie(MovieDTO movie) {
        try {
            MovieDocument movieDocument = convertDtoToDocument(movie);
            movieMongoDbRepository.saveMovie(movieDocument);

            return convertModelToDto(movieDocument);
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error while saving movie data");
        }
    }

    public List<MovieDTO> getAllMovies() {
    try {
        List<MovieDocument> movieDocuments = movieMongoDbRepository.getAllMovies();

        return movieDocuments.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
    }catch (Exception e) {
        throw new DatabaseConnectionException("Error while retrieving movies");
    }
    }

    public Optional<MovieDTO> getMovieById(String id) {

        Optional<MovieDocument> movieDocumentOptional = movieMongoDbRepository.getMovie(id);

        if (movieDocumentOptional.isPresent()) {
            return Optional.of(convertModelToDto(movieDocumentOptional.get()));
        }
        throw new MovieNotFoundException("Movie with id " + id + " not found");
    }

    public MovieDTO updateMovie(String id, MovieDTO updatedMovie) {

        Optional<MovieDocument> existingMovieDocument = movieMongoDbRepository.getMovie(id);
        if (existingMovieDocument.isPresent()) {
            MovieDocument movieDocument = existingMovieDocument.get();
            updateDocument(movieDocument, updatedMovie);
            movieMongoDbRepository.saveMovie(movieDocument);
        }

        return updatedMovie;
    }

    private void updateDocument(MovieDocument movieDocument, MovieDTO updatedMovie) {
        if (updatedMovie.getTitle() != null) movieDocument.setTitle(updatedMovie.getTitle());
        if (updatedMovie.getDirector() != null) movieDocument.setDirector(updatedMovie.getDirector());
        if (updatedMovie.getReleaseYear() > 0) movieDocument.setReleaseYear(updatedMovie.getReleaseYear());
        if (updatedMovie.getGenre() != null) movieDocument.setGenre(updatedMovie.getGenre());
        if (updatedMovie.getDuration() > 0) movieDocument.setDuration(updatedMovie.getDuration());
        if (updatedMovie.getRating() > 0) movieDocument.setRating(updatedMovie.getRating());
        if (updatedMovie.getCoverImage() != null) movieDocument.setCoverImage(updatedMovie.getCoverImage());
        if (updatedMovie.getActors() != null) movieDocument.setActors(updatedMovie.getActors());
        if (updatedMovie.getShowtimes() != null) movieDocument.setShowtimes(
                updatedMovie.getShowtimes().stream()
                        .map(showtimeDTO -> new MovieDocument.ShowtimeDocument(showtimeDTO.getDay(), showtimeDTO.getTimes()))
                        .collect(Collectors.toList())
        );
    }

    public void deleteMovie(String id) {
        movieMongoDbRepository.deleteMovie(id);
    }

    public List<MovieDTO> saveMovies(List<MovieDTO> movies) {
        try {
            List<MovieDocument> movieDocuments = movies.stream()
                    .map(this::convertDtoToDocument)
                    .collect(Collectors.toList());
            movieMongoDbRepository.saveAll(movieDocuments);

            return movies;
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error while saving multiple movies");
        }
    }
    private MovieDTO convertModelToDto(MovieModel movieModel) {
        return modelMapper.map(movieModel, MovieDTO.class);
    }
    private MovieDocument convertDtoToDocument(MovieDTO movieDTO){
        return modelMapper.map(movieDTO, MovieDocument.class);
    }
}