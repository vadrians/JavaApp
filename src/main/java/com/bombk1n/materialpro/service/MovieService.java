package com.bombk1n.materialpro.service;

import com.bombk1n.materialpro.dao.IMovieRepository;
import com.bombk1n.materialpro.dto.MovieDTO;
import com.bombk1n.materialpro.model.MovieDocument;
import com.bombk1n.materialpro.model.MovieEntity;
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

    private final IMovieRepository<MovieEntity> moviePostgresRepository;
    private final IMovieRepository<MovieDocument> movieMongoDbRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieService(
            @Qualifier("moviePostgresRepository") IMovieRepository<MovieEntity> moviePostgresRepository,
            @Qualifier("movieMongoDbRepository") IMovieRepository<MovieDocument> movieMongoDbRepository,
            ModelMapper modelMapper) {
        this.moviePostgresRepository = moviePostgresRepository;
        this.movieMongoDbRepository = movieMongoDbRepository;
        this.modelMapper = modelMapper;
    }


    public MovieDTO saveMovie(MovieDTO movie) {

        MovieEntity movieEntity = convertDtoToEntity(movie);
        moviePostgresRepository.saveMovie(movieEntity);

        MovieDocument movieDocument = convertDtoToDocument(movie);
        movieMongoDbRepository.saveMovie(movieDocument);

        return convertModelToDto(movieDocument);
    }

    public List<MovieDTO> getAllMovies() {

        List<MovieDocument> movieDocuments = movieMongoDbRepository.getAllMovies();

        return movieDocuments.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
    }

    public Optional<MovieDTO> getMovieById(String id) {

        Optional<MovieEntity> movieEntityOptional = moviePostgresRepository.getMovie(id);

        Optional<MovieDocument> movieDocumentOptional = movieMongoDbRepository.getMovie(id);

        if (movieEntityOptional.isPresent()) {
            return Optional.of(convertModelToDto(movieEntityOptional.get()));
        } else if (movieDocumentOptional.isPresent()) {
            return Optional.of(convertModelToDto(movieDocumentOptional.get()));
        }
        return Optional.empty();
    }

    public MovieDTO updateMovie(String id, MovieDTO updatedMovie) {

        Optional<MovieEntity> existingMovieEntity = moviePostgresRepository.getMovie(id);
        if (existingMovieEntity.isPresent()) {
            MovieEntity movieEntity = existingMovieEntity.get();

            if (updatedMovie.getTitle() != null) movieEntity.setTitle(updatedMovie.getTitle());
            if (updatedMovie.getDirector() != null) movieEntity.setDirector(updatedMovie.getDirector());
            if (updatedMovie.getReleaseYear() > 0) movieEntity.setReleaseYear(updatedMovie.getReleaseYear());
            if (updatedMovie.getGenre() != null) movieEntity.setGenre(updatedMovie.getGenre());
            if (updatedMovie.getDuration() > 0) movieEntity.setDuration(updatedMovie.getDuration());
            if (updatedMovie.getRating() > 0) movieEntity.setRating(updatedMovie.getRating());
            if (updatedMovie.getCoverImage() != null) movieEntity.setCoverImage(updatedMovie.getCoverImage());
            if (updatedMovie.getActors() != null && !updatedMovie.getActors().isEmpty())
                movieEntity.setActors(updatedMovie.getActors());
            if (updatedMovie.getShowtimes() != null && !updatedMovie.getShowtimes().isEmpty())
                movieEntity.setShowtimes(updatedMovie.getShowtimes().stream()
                        .map(showtimeDTO -> new MovieEntity.ShowtimeEntity(showtimeDTO.getDay(), showtimeDTO.getTimes()))
                        .collect(Collectors.toList()));

            moviePostgresRepository.saveMovie(movieEntity);
        }

        Optional<MovieDocument> existingMovieDocument = movieMongoDbRepository.getMovie(id);
        if (existingMovieDocument.isPresent()) {
            MovieDocument movieDocument = existingMovieDocument.get();

            if (updatedMovie.getTitle() != null) movieDocument.setTitle(updatedMovie.getTitle());
            if (updatedMovie.getDirector() != null) movieDocument.setDirector(updatedMovie.getDirector());
            if (updatedMovie.getReleaseYear() > 0) movieDocument.setReleaseYear(updatedMovie.getReleaseYear());
            if (updatedMovie.getGenre() != null) movieDocument.setGenre(updatedMovie.getGenre());
            if (updatedMovie.getDuration() > 0) movieDocument.setDuration(updatedMovie.getDuration());
            if (updatedMovie.getRating() > 0) movieDocument.setRating(updatedMovie.getRating());
            if (updatedMovie.getCoverImage() != null) movieDocument.setCoverImage(updatedMovie.getCoverImage());
            if (updatedMovie.getActors() != null && !updatedMovie.getActors().isEmpty())
                movieDocument.setActors(updatedMovie.getActors());
            if (updatedMovie.getShowtimes() != null && !updatedMovie.getShowtimes().isEmpty())
                movieDocument.setShowtimes(updatedMovie.getShowtimes().stream()
                        .map(showtimeDTO -> new MovieDocument.ShowtimeDocument(showtimeDTO.getDay(), showtimeDTO.getTimes()))
                        .collect(Collectors.toList()));

            movieMongoDbRepository.saveMovie(movieDocument);
        }

        return updatedMovie;
    }

    public void deleteMovie(String id) {
        moviePostgresRepository.deleteMovie(id);
        movieMongoDbRepository.deleteMovie(id);
    }

    public List<MovieDTO> saveMovies(List<MovieDTO> movies) {
        List<MovieEntity> movieEntities = movies.stream()
                .map(this::convertDtoToEntity)
                .collect(Collectors.toList());
        moviePostgresRepository.saveAll(movieEntities);

        List<MovieDocument> movieDocuments = movies.stream()
                .map(this::convertDtoToDocument)
                .collect(Collectors.toList());
        movieMongoDbRepository.saveAll(movieDocuments);

        return movies;
    }
    private MovieDTO convertModelToDto(MovieModel movieModel) {
        return modelMapper.map(movieModel, MovieDTO.class);
    }
    private MovieEntity convertDtoToEntity(MovieDTO movieDTO) {
        return modelMapper.map(movieDTO, MovieEntity.class);
    }
    private MovieDocument convertDtoToDocument(MovieDTO movieDTO){
        return modelMapper.map(movieDTO, MovieDocument.class);
    }
}