package com.bombk1n.materialpro.dao.postgres;

import com.bombk1n.materialpro.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyJpaRepository extends JpaRepository<MovieEntity, Long> {
}
