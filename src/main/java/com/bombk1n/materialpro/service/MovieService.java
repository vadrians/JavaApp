package com.bombk1n.materialpro.service;

import com.bombk1n.materialpro.dao.IMovieRepository;
import com.bombk1n.materialpro.dto.MovieDTO;
import com.bombk1n.materialpro.exceptions.DatabaseConnectionException;
import com.bombk1n.materialpro.exceptions.MovieNotFoundException;
import com.bombk1n.materialpro.model.MovieEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final IMovieRepository moviePostgresRepository;
    private final IMovieRepository movieMongoDbRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieService(
            @Qualifier("moviePostgresRepository") IMovieRepository moviePostgresRepository,
            @Qualifier("movieMongoRepository") IMovieRepository movieMongoDbRepository,
            ModelMapper modelMapper) {
        this.moviePostgresRepository = moviePostgresRepository;
        this.movieMongoDbRepository = movieMongoDbRepository;
        this.modelMapper = modelMapper;
    }


    public MovieDTO saveMovie(MovieDTO movie) {
        try {
            MovieEntity movieEntity = convertDtoToEntity(movie);
            moviePostgresRepository.saveMovie(movieEntity);
            movieMongoDbRepository.saveMovie(movieEntity);

            return convertEntityToDto(movieEntity);
        } catch (Exception e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    public List<MovieDTO> getAllMovies() {
        try {
            List<MovieEntity> movieEntities = movieMongoDbRepository.getAllMovies();

            return movieEntities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    public Optional<MovieDTO> getMovieById(String id) {

        Optional<MovieEntity> moviePostgresEntityOptional = moviePostgresRepository.getMovie(id);
        Optional<MovieEntity> movieMongoEntityOptional = movieMongoDbRepository.getMovie(id);

        if (moviePostgresEntityOptional.isPresent()) {
            return Optional.of(convertEntityToDto(moviePostgresEntityOptional.get()));
        } else if (movieMongoEntityOptional.isPresent()) {
            return Optional.of(convertEntityToDto(movieMongoEntityOptional.get()));
        }
        throw new MovieNotFoundException("Movie with id " + id + " not found");
    }

    public MovieDTO updateMovie(String id, MovieDTO updatedMovie) {

        moviePostgresRepository.getMovie(id).ifPresent(entity -> {
            updateEntityFields(entity, updatedMovie);
            moviePostgresRepository.saveMovie(entity);
        });

        movieMongoDbRepository.getMovie(id).ifPresent(entity -> {
            updateEntityFields(entity, updatedMovie);
            movieMongoDbRepository.saveMovie(entity);
        });

         return moviePostgresRepository.getMovie(id)
                .map(this::convertEntityToDto)
                .orElseThrow(() -> new MovieNotFoundException("Movie with id " + id + " not found"));
    }

    private void updateEntityFields(MovieEntity entity, MovieDTO updatedMovie) {
        if (updatedMovie.getTitle() != null) entity.setTitle(updatedMovie.getTitle());
        if (updatedMovie.getDirector() != null) entity.setDirector(updatedMovie.getDirector());
        if (updatedMovie.getReleaseYear() > 0) entity.setReleaseYear(updatedMovie.getReleaseYear());
        if (updatedMovie.getGenre() != null) entity.setGenre(updatedMovie.getGenre());
        if (updatedMovie.getDuration() > 0) entity.setDuration(updatedMovie.getDuration());
        if (updatedMovie.getRating() > 0) entity.setRating(updatedMovie.getRating());
        if (updatedMovie.getCoverImage() != null) entity.setCoverImage(updatedMovie.getCoverImage());
    }


    public void deleteMovie(String id) {
        moviePostgresRepository.deleteMovie(id);
        movieMongoDbRepository.deleteMovie(id);
    }

    public List<MovieDTO> saveMovies(List<MovieDTO> movies) {
        try {
            List<MovieEntity> movieEntities = movies.stream()
                    .map(this::convertDtoToEntity)
                    .collect(Collectors.toList());
            moviePostgresRepository.saveAll(movieEntities);
            movieMongoDbRepository.saveAll(movieEntities);
            return movies;
        } catch (Exception e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    private MovieDTO convertEntityToDto(MovieEntity movieModel) {
        return modelMapper.map(movieModel, MovieDTO.class);
    }

    private MovieEntity convertDtoToEntity(MovieDTO movieDTO) {
        return modelMapper.map(movieDTO, MovieEntity.class);
    }

}