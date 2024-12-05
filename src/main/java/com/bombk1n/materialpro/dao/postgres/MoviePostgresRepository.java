package com.bombk1n.materialpro.dao.postgres;

import com.bombk1n.materialpro.dao.IMovieRepository;
import com.bombk1n.materialpro.model.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MoviePostgresRepository implements IMovieRepository<MovieEntity> {

    private final MyJpaRepository jpaRepository;

    @Autowired
    public MoviePostgresRepository(MyJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public MovieEntity saveMovie(MovieEntity movie) {
        return jpaRepository.save(movie);
    }

    @Override
    public List<MovieEntity> getAllMovies() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<MovieEntity> getMovie(String id) {
        return jpaRepository.findById(Long.parseLong(id));
    }

    @Override
    public MovieEntity updateMovie(String id, MovieEntity updatedMovie) {
        if (jpaRepository.existsById(Long.parseLong(id))) {
            updatedMovie.setId(Long.parseLong(id));
            return jpaRepository.save(updatedMovie);
        }
        return null;
    }

    @Override
    public void deleteMovie(String id) {
        jpaRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public boolean existsMovie(String id) {
        return jpaRepository.existsById(Long.parseLong(id));
    }

    @Override
    public List<MovieEntity> saveAll(List<MovieEntity> movies) {
        return jpaRepository.saveAll(movies);
    }
}
