package com.bombk1n.materialpro.dao;

import com.bombk1n.materialpro.dto.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
