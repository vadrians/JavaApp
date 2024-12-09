package com.bombk1n.materialpro.dao.postgres;

import com.bombk1n.materialpro.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MyJpaRepository extends JpaRepository<MovieEntity, String> {
}
