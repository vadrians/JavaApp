package com.bombk1n.materialpro.dao.mongoDB;

import com.bombk1n.materialpro.dao.IMovieRepository;

import com.bombk1n.materialpro.model.MovieDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;



@Repository
public class MovieMongoDbRepository implements IMovieRepository<MovieDocument> {

    private final MyMongoRepository mongoRepository;

    @Autowired
    public MovieMongoDbRepository(MyMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public MovieDocument saveMovie(MovieDocument movie) {
        return mongoRepository.save(movie);
    }

    @Override
    public List<MovieDocument> getAllMovies() {
        return mongoRepository.findAll();
    }

    @Override
    public Optional<MovieDocument> getMovie(String id) {
        return mongoRepository.findById(id);
    }

    @Override
    public MovieDocument updateMovie(String id, MovieDocument updatedMovie) {
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
    public List<MovieDocument> saveAll(List<MovieDocument> movies) {
        return mongoRepository.saveAll(movies);
    }
}