package com.bombk1n.materialpro.dao.mongoDB;

import com.bombk1n.materialpro.dao.IMovieRepository;
import com.bombk1n.materialpro.model.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;



@Repository
public class MovieMongoRepository implements IMovieRepository {

    private final MyMongoRepository mongoRepository;

    @Autowired
    public MovieMongoRepository(MyMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public MovieEntity saveMovie(MovieEntity movie) {
        return mongoRepository.save(movie);
    }

    @Override
    public List<MovieEntity> getAllMovies() {
        return mongoRepository.findAll();
    }

    @Override
    public Optional<MovieEntity> getMovie(String id) {
        return mongoRepository.findById(id);
    }

    @Override
    public MovieEntity updateMovie(String id, MovieEntity updatedMovie) {
        if (mongoRepository.existsById(id)) {
            updatedMovie.setId(id);
            return mongoRepository.save(updatedMovie);
        }
        return null;
    }

    @Override
    public void deleteMovie(String id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public boolean existsMovie(String id) {
        return mongoRepository.existsById(id);
    }

    @Override
    public List<MovieEntity> saveAll(List<MovieEntity> movies) {
        return mongoRepository.saveAll(movies);
    }
}