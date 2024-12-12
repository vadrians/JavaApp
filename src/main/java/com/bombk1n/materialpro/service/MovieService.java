package com.bombk1n.materialpro.service;

import com.bombk1n.materialpro.dao.IMovieRepository;
import com.bombk1n.materialpro.dto.MovieDTO;
import com.bombk1n.materialpro.exceptions.DatabaseConnectionException;
import com.bombk1n.materialpro.exceptions.MovieNotFoundException;
import com.bombk1n.materialpro.model.MovieEntity;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieService {

    //    private final IMovieRepository moviePostgresRepository; //not used
    private final IMovieRepository movieMongoDbRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieService(
            @Qualifier("movieMongoRepository") IMovieRepository movieMongoDbRepository,
            ModelMapper modelMapper) {
        this.movieMongoDbRepository = movieMongoDbRepository;
        this.modelMapper = modelMapper;
    }


    public MovieDTO saveMovie(MovieDTO movie) {
        try {
            MovieEntity movieEntity = convertDtoToEntity(movie);
            movieMongoDbRepository.saveMovie(movieEntity);
            return convertEntityToDto(movieEntity);
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error saving movie: " + e.getMessage());
        }
    }

    public List<MovieDTO> getAllMovies() {
        try {
            List<MovieEntity> movieEntities = movieMongoDbRepository.getAllMovies();
            return movieEntities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error fetching all movies: " + e.getMessage());
        }
    }

    public MovieDTO getMovieById(String id) {
        return movieMongoDbRepository.getMovie(id)
                .map((this::convertEntityToDto))
                .orElseThrow(() -> new MovieNotFoundException("Movie with id " + id + " not found"));
    }

    public MovieDTO updateMovie(String id, MovieDTO updatedMovie) {
        try {
            Optional<MovieEntity> mongoMovie = movieMongoDbRepository.getMovie(id);
            if (mongoMovie.isEmpty()) {
                throw new MovieNotFoundException("Movie with id " + id + " not found");
            }
            MovieEntity movieEntity = mongoMovie.get();
            updateEntityFields(movieEntity, updatedMovie);

            movieMongoDbRepository.saveMovie(movieEntity);

            return convertEntityToDto(mongoMovie.get());
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error updating movie with id " + id + ": " + e.getMessage());
        }
    }

    private void updateEntityFields(MovieEntity entity, MovieDTO updatedMovie) {
        Optional.ofNullable(updatedMovie.getTitle()).ifPresent(entity::setTitle);
        Optional.ofNullable(updatedMovie.getDirector()).ifPresent(entity::setDirector);
        if (updatedMovie.getReleaseYear() > 0) entity.setReleaseYear(updatedMovie.getReleaseYear());
        Optional.ofNullable(updatedMovie.getGenre()).ifPresent(entity::setGenre);
        if (updatedMovie.getDuration() > 0) entity.setDuration(updatedMovie.getDuration());
        if (updatedMovie.getRating() > 0) entity.setRating(updatedMovie.getRating());
        Optional.ofNullable(updatedMovie.getCoverImage()).ifPresent(entity::setCoverImage);
    }

    public void deleteMovie(String id) {
        try {
            Optional<MovieEntity> movieEntity = movieMongoDbRepository.getMovie(id);
            if (movieEntity.isEmpty()) {
                throw new MovieNotFoundException("Movie with id " + id + " not found");
            }
            movieMongoDbRepository.deleteMovie(id);
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error deleting movie with id " + id + ": " + e.getMessage());
        }
    }

    public List<MovieDTO> saveMovies(List<MovieDTO> movies) {
        try {
            List<MovieEntity> movieEntities = movies.stream()
                    .map(this::convertDtoToEntity)
                    .collect(Collectors.toList());
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
        if (movieDTO.getId() == null) {
            movieDTO.setId(new ULID().nextULID());
        }
        return modelMapper.map(movieDTO, MovieEntity.class);
    }

}